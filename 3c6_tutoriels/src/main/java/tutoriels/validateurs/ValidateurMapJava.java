/*
Copyright (C) (2020) (Mathieu Bergeron) (mathieu.bergeron@cmontmorency.qc.ca)

This file is part of Ntro, an application framework designed with teaching in mind.

This is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

*/
package tutoriels.validateurs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ca.ntro.core.introspection.Introspector;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.mappage.Cle;
import tutoriels.mappage.MapJava;
import tutoriels.mappage.MapJavaReference;
import tutoriels.mappage.TesteurDeMap;

public class ValidateurMapJava extends Validateur {

	private static TesteurDeMap testeurDeMap;

	private static int tailleCleMin = 6;
	private static int tailleCleMax = 10;
	private static final char[] alphabet = new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t', 'u', 'v','w','x', 'y', 'z'};
			                                           //'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T', 'U', 'V','W','X', 'Y', 'Z',
			                                           //'0','1','2','3','4','5','6','7','8','9','_'};
	
	public static String cleAleatoire(int tailleMin, int tailleMax) {
		StringBuilder builder = new StringBuilder();
		
		int tailleCle = tailleMin + alea.nextInt(tailleMax - tailleMin);

		for(int i = 0; i < tailleCle; i++) {
			builder.append(alphabet[alea.nextInt(alphabet.length)]);
		}
		
		return builder.toString();
	}

	public static String[] clesAleatoires(int taille){
		
		String[] cles = new String[taille];
		
		for(int i = 0; i < taille; i++) {
			cles[i] = cleAleatoire(tailleCleMin, tailleCleMax);
		}
		
		return cles;
	}
	
	
	public static Map<String, Integer> mapAleatoire(String[] cles){
		Map<String, Integer> map = new HashMap<>();
		
		for(int i = 0; i < cles.length; i++) {
			map.put(cles[i], alea.nextInt(cles.length));
		}

		return map;
	}
	
	
	@SuppressWarnings("unchecked")
	private static MapJava<Cle<String>, Integer> creerMapVide(){

		MapJava<Cle<String>, Integer> mapVide = null;
		
		Method methodeNouveauMap = Introspector.findMethodByName(testeurDeMap.getClass(), "nouveauMap");
		
		try {

			mapVide = (MapJava<Cle<String>, Integer>) methodeNouveauMap.invoke(testeurDeMap);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			Log.fatalError("Impossible d'appeler nouveauMap", e);
		}
		
		return mapVide;
	}

	@SuppressWarnings("unchecked")
	private static Cle<String> creerCle(String valeur){

		Cle<String> cle = null;
		
		Method methodeNouvelleCle = Introspector.findMethodByName(testeurDeMap.getClass(), "nouvelleCle");
		
		try {

			cle = (Cle<String>) methodeNouvelleCle.invoke(testeurDeMap, valeur);

		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

			Log.fatalError("Impossible d'appeler nouvelleCle", e);
		}
		
		return cle;
	}
	

	private static class PaireDeMap {
		public MapJava<Cle<String>, Integer> aTester;
		public MapJava<Cle<String>, Integer> reference;
		
		public PaireDeMap() {
			aTester = creerMapVide();
			reference = new MapJavaReference<String, Cle<String>, Integer>();
		}

		public boolean valider() {
			return aTester.equals(reference);
		}
	}
	
	
	
	
	public static void valider(ReportNodeViewModel report, 
			                   String mapName, 
			                   TesteurDeMap testeurDeMap, 
			                   boolean ecrireGraphes,
			                   int tailleCleMin,
			                   int tailleCleMax) {
		
		ValidateurMapJava.tailleCleMin = tailleCleMin;
		ValidateurMapJava.tailleCleMax = tailleCleMax;

		ValidateurMapJava.ecrireGraphes = ecrireGraphes;

		ValidateurMapJava.testeurDeMap = testeurDeMap;
		ValidateurMapJava.continuerDeValider = true;

		int testId = 0;
		
		List<PaireDeMap> tests = new ArrayList<>();
		
		// 1 liste vide qu'on va tester 5 fois
		PaireDeMap vide01 = new PaireDeMap();
		tests.add(vide01);

		try {

			htmlOut = new FileWriter(new File(htmlFilename(vide01.aTester, mapName)));
			htmlOut.write("<html>");
			htmlOut.write("<body>");
			htmlOut.write("<div style='max-width:80%; margin:auto;'>");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// tester put 10 fois
		for(int i = 0; i < 20; i++) {
			validatePut(report, vide01, mapName, testId);
			testId++;
		}

		// ajouter un autre vide
		PaireDeMap vide02 = new PaireDeMap();

		// tester get sur vide, puis 5 fois valide et 5 fois invalide
		validateGet(report, vide02, mapName, testId);
		testId++;

		for(PaireDeMap paire : tests) {
			for(int i = 0; i < 10; i++) {
				validateGet(report, paire, mapName, testId);
				testId++;
			}
		}

		// tester size sur vide
		validateSize(report, vide02, mapName, testId);
		testId++;

		// teste size sur plein
		for(PaireDeMap paire : tests) {
			validateSize(report, paire, mapName, testId);
			testId++;
		}

		// tester size sur vide
		validateIsEmpty(report, vide02, mapName, testId);
		testId++;

		// teste size sur plein
		for(PaireDeMap paire : tests) {
			validateIsEmpty(report, paire, mapName, testId);
			testId++;
		}

		// tester containsKey sur vide, puis 5 fois valide et 5 fois invalide
		validateContainsKey(report, vide02, mapName, testId);
		testId++;

		for(PaireDeMap paire : tests) {
			for(int i = 0; i < 10; i++) {
				validateContainsKey(report, paire, mapName, testId);
				testId++;
			}
		}

		// tester containsKey sur vide, puis 5 fois valide et 5 fois invalide
		validateContainsValue(report, vide02, mapName, testId);
		testId++;

		for(PaireDeMap paire : tests) {
			for(int i = 0; i < 10; i++) {
				validateContainsValue(report, paire, mapName, testId);
				testId++;
			}
		}

		//tester keys sur vide et une fois sur plein
		validateKeys(report, vide02, mapName, testId);
		testId++;

		for(PaireDeMap paire : tests) {
			validateKeys(report, paire, mapName, testId);
			testId++;
		}

		// tester containsKey sur vide, puis 3 fois valide et 3 fois invalide
		validateRemove(report, vide02, mapName, testId);
		testId++;

		for(PaireDeMap paire : tests) {
			for(int i = 0; i < 6; i++) {
				validateRemove(report, paire, mapName, testId);
				testId++;
			}
		}

		//tester clear sur vide et une fois sur plein
		validateClear(report, vide02, mapName, testId);
		testId++;

		for(PaireDeMap paire : tests) {
			validateClear(report, paire, mapName, testId);
			testId++;
		}

		report.setExpectedNumberOfSubReports(testId);

		try {

			htmlOut.write("</div>");
			htmlOut.write("</body>");
			htmlOut.write("</html>");
			htmlOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void validateClear(ReportNodeViewModel report, 
			                          PaireDeMap test, 
			                          String mapName, 
			                          int testId) {

		T.call(ValidateurMapJava.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurApres(page);

		valeurAvantOperation(test, mapName, testName, page);
		
		try {

			page.append("<td>");
			page.append(String.format("map.clear();"));
			page.append("</td>");

			test.aTester.clear();
			test.reference.clear();
		
			if(test.valider()) {
				valeurApresOK(test, mapName, testName, thisReport, page);
			}else {
				valeurApresErreur(test, mapName, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}
	

	private static void validateKeys(ReportNodeViewModel report, 
			                         PaireDeMap test, 
			                         String mapName,
			                         int testId) {

		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurDeRetour(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {

			page.append("<td>");
			page.append(String.format("map.keys();"));
			page.append("</td>");

			ListeDeCles<String> resultatUsager = new ListeDeCles<String>(test.aTester.keys());
			ListeDeCles<String> resultatReference = new ListeDeCles<String>(test.reference.keys());
			
			if(resultatUsager.equals(resultatReference)) {
				valeurDeRetourApresOK(resultatUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(resultatUsager, resultatReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}
	
	
	private static void validateRemove(ReportNodeViewModel report, 
			                           PaireDeMap test, 
			                           String mapName,
			                           int testId) {

		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurApres(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {
			List<Cle<String>> cles = test.reference.keys();
			Cle<String> cle = null;
			if(cles.size() > 0 && alea.nextBoolean()) {
				cle = cles.get(alea.nextInt(cles.size()));
				cle = creerCle(cle.getValeur());
			}else {
				int tailleCle = 3;
				String valeurCle = cleAleatoire(tailleCleMin, tailleCleMax);
				cle = creerCle(valeurCle);
			}
			
			page.append("<td>");
			page.append(String.format("map.remove(\"%s\");", cle.getValeur()));
			page.append("</td>");

			test.aTester.remove(cle);
			test.reference.remove(cle);
			
			if(test.valider()) {
				valeurApresOK(test, mapName, testName, thisReport, page);
			}else {
				valeurApresErreur(test, mapName, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}
	
	
	
	private static void validateContainsValue(ReportNodeViewModel report, 
			                                  PaireDeMap test, 
			                                  String mapName,
			                                  int testId) {
		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurDeRetour(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {
			List<Cle<String>> cles = test.reference.keys();
			Cle<String> cle = null;
			Integer valeur = alea.nextInt(100);
			if(cles.size() > 0 && alea.nextBoolean()) {
				cle = cles.get(alea.nextInt(cles.size()));
				cle = creerCle(cle.getValeur());
			}
			
			page.append("<td>");
			page.append(String.format("map.containsValue(\"%s\");", valeur));
			
			page.append("</td>");

			boolean resultatUsager = test.aTester.containsValue(valeur);
			boolean resultatReference = test.reference.containsValue(valeur);
			
			if(resultatUsager == resultatReference) {
				valeurDeRetourApresOK(resultatUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(resultatUsager, resultatReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}
	
	
	private static void validateContainsKey(ReportNodeViewModel report, 
			                                PaireDeMap test, 
			                                String mapName,
			                                int testId) {
		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurDeRetour(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {
			List<Cle<String>> cles = test.reference.keys();
			Cle<String> cle = null;
			if(cles.size() > 0 && alea.nextBoolean()) {
				cle = cles.get(alea.nextInt(cles.size()));
				cle = creerCle(cle.getValeur());
			}else {
				int tailleCle = 3;
				String valeurCle = cleAleatoire(tailleCleMin, tailleCleMax);
				cle = creerCle(valeurCle);
			}
			
			page.append("<td>");
			page.append(String.format("map.containsKey(\"%s\");", cle.getValeur()));
			page.append("</td>");

			boolean resultatUsager = test.aTester.containsKey(cle);
			boolean resultatReference = test.reference.containsKey(cle);
			
			if(resultatUsager == resultatReference) {
				valeurDeRetourApresOK(resultatUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(resultatUsager, resultatReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}

	

	private static void validateIsEmpty(ReportNodeViewModel report, 
			                            PaireDeMap test, 
			                            String mapName,
			                            int testId) {

		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurDeRetour(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {
			page.append("<td>");
			page.append(String.format("map.isEmpty();"));
			page.append("</td>");

			boolean resultatUsager = test.aTester.isEmpty();
			boolean resultatReference = test.reference.isEmpty();
			
			if(resultatUsager == resultatReference) {
				valeurDeRetourApresOK(resultatUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(resultatUsager, resultatReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}

	private static void validateSize(ReportNodeViewModel report, 
			                         PaireDeMap test, 
			                         String mapName,
			                         int testId) {

		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurDeRetour(page);

		valeurAvantOperation(test, mapName, testName, page);
		
		try {

			page.append("<td>");
			page.append(String.format("map.size();"));
			page.append("</td>");

			int resultatUsager = test.aTester.size();
			int resultatReference = test.reference.size();
			
			if(resultatUsager == resultatReference) {
				valeurDeRetourApresOK(resultatUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(resultatUsager, resultatReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}

	private static void validateGet(ReportNodeViewModel report, 
			                        PaireDeMap test, 
			                        String mapName,
			                        int testId) {

		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();

		String testName = testName(testId);
		report.updateTestId(testId);
		
		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurDeRetour(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {
			List<Cle<String>> cles = test.reference.keys();
			Cle<String> cle = null;
			if(cles.size() > 0 && alea.nextBoolean()) {
				cle = cles.get(alea.nextInt(cles.size()));
				cle = creerCle(cle.getValeur());
			}else {
				int tailleCle = 3;
				String valeurCle = cleAleatoire(tailleCleMin, tailleCleMax);
				cle = creerCle(valeurCle);
			}
			
			page.append("<td>");
			page.append(String.format("map.get(\"%s\");", cle.getValeur()));
			page.append("</td>");

			Object resultatUsager = test.aTester.get(cle);
			Object resultatReference = test.reference.get(cle);
			
			boolean siEgal = false;
			if(resultatUsager == null && resultatReference == null) {
				siEgal = true;
			}else if(resultatUsager != null && resultatUsager.equals(resultatReference)) {
				siEgal = true;
			}
			
			if(siEgal) {
				valeurDeRetourApresOK(resultatUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(resultatUsager, resultatReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}

	private static void validatePut(ReportNodeViewModel report, 
			                        PaireDeMap test, 
			                        String mapName,
			                        int testId) {

		T.call(ValidateurMapJava.class);

		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);

		thisReport.setTitle(testName);
		htmlTestName(testName);

		tablePourValeurApres(page);
		valeurAvantOperation(test, mapName, testName, page);
		
		try {
			List<Cle<String>> cles = test.reference.keys();
			Cle<String> cle = null;
			int valeur = alea.nextInt(100);
			if(cles.size() > 0 && alea.nextBoolean()) {
				cle = cles.get(alea.nextInt(cles.size()));
				cle = creerCle(cle.getValeur());
			}else {
				int tailleCle = 3;
				String valeurCle = cleAleatoire(tailleCleMin, tailleCleMax);
				cle = creerCle(valeurCle);
			}

			page.append("<td>");
			page.append(String.format("map.put(\"%s\",&nbsp;%s);", cle.getValeur(), valeur));
			page.append("</td>");
			
			test.aTester.put(cle, valeur);
			test.reference.put(cle, valeur);
			
			if(test.valider()) {
				valeurApresOK(test, mapName, testName, thisReport, page);
			}else {
				valeurApresErreur(test, mapName, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurPourCrash(thisReport, e);
			
		}
		
	}

	private static void valeurApresErreur(PaireDeMap test, 
			                              String mapName,
			 						      String testName,
			                              ReportNodeViewModel thisReport, 
			                              StringBuilder page) {

		String graphName = graphName(mapName, testName, "erreur");
		writeObjectGraph(test.aTester, graphName);

		String graphSrc = graphSrc(graphName);

		page.append("<td>");
		page.append(test.aTester);
		if(ecrireGraphes) {
			page.append("<br>");
			page.append("<img src='" + graphSrc + "'/>");
		}
		page.append("<br>");
		page.append("<br>");
		page.append("^^^ ERREUR ^^^");
		page.append("<br>");
		page.append("<br>");
		page.append("valeur attendue:<br><br>");
		page.append(test.reference);
		page.append("</td>");
		
		/*
		page.append("valeur après l'opération (ERREUR)");
		page.append("</td>");
		page.append("</tr>");
		page.append("<tr>");
		page.append("<td>");
		page.append(test.reference);
		page.append("</td>");
		page.append("<td>");
		page.append("valeur attendue après l'opération");
		page.append("</td>");
		page.append("</tr>");
		*/
		thisReport.setState(ValidationState.ERROR);
		
		continuerDeValider = false;
	}

	private static void valeurDeRetourApresErreur(Object retourErreur, Object retourOK, ReportNodeViewModel thisReport, StringBuilder page) {
		page.append("<td>");
		page.append(retourErreur);
		page.append("<br>");
		page.append("<br>");
		page.append("valeur de retour attendue: ");
		page.append(retourOK);
		page.append("</td>");
		thisReport.setState(ValidationState.ERROR);
		
		continuerDeValider = false;
	}

	private static void valeurApresOK(PaireDeMap test, 
			                          String mapName,
							          String testName,
			                          ReportNodeViewModel thisReport, 
			                          StringBuilder page) {

		String graphName = graphName(mapName, testName, "ok");
		writeObjectGraph(test.aTester, graphName);
		
		String graphSrc = graphSrc(graphName);

		page.append("<td>");
		page.append(test.aTester);
		if(ecrireGraphes) {
			page.append("<br>");
			page.append("<img src='" + graphSrc + "'/>");
		}
		page.append("</td>");
		page.append("</tr>");
		thisReport.setState(ValidationState.OK);
	}

	private static void valeurDeRetourApresOK(Object valeurOK, ReportNodeViewModel thisReport, StringBuilder page) {
		page.append("<td>");
		page.append(valeurOK);
		page.append("</td>");
		thisReport.setState(ValidationState.OK);
	}

	private static void valeurAvantOperation(PaireDeMap test, 
			                                 String mapName,
			                                 String testName, 
			                                 StringBuilder page) {
		
		String graphName = graphName(mapName, testName, "avant");
		writeObjectGraph(test.aTester, graphName);
		
		String graphSrc = graphSrc(graphName);

		page.append("<tr>");
		page.append("<td>");
		page.append(test.aTester);
		if(ecrireGraphes) {
			page.append("<br>");
			page.append("<img src='" + graphSrc + "'/>");
		}
		page.append("</td>");
	}

	private static void valeurPourCrash(ReportNodeViewModel thisReport, Exception e) {
		thisReport.setState(ValidationState.CRASH);
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);
		e.printStackTrace(printer);
		thisReport.setHtmlPage("<pre>" + writer.toString() + "</pre>");
		
		continuerDeValider = false;
	}
	
	private static String graphName(String mapName, String testName, String suffix) {
		return mapName + "_" + testName + "_" + suffix;
	}


}
