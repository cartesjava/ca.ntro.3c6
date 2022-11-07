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
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ca.ntro.core.initialization.Ntro;
import ca.ntro.core.reflection.object_graph.ObjectGraph;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.arbre.Arbre;
import tutoriels.arbre.ArbreReference;
import tutoriels.arbre.Noeud;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;

public class ValidateurArbre extends Validateur {
	
	private static class PaireArbres {
		public Arbre<Character> aTester;
		public Arbre<Character> reference;
		
		public static Object atelier;
		public static Method methodeCreerArbre;
		
		public PaireArbres() {
				aTester = creerArbreVide(atelier, methodeCreerArbre);
				reference = new ArbreReference<>();
		}

		@SuppressWarnings("unchecked")
		private Arbre<Character> creerArbreVide(Object atelier, Method methodeCreerArbre){
			
			Arbre<Character> arbreVide = null;
			
			try {

				arbreVide = (Arbre<Character>) methodeCreerArbre.invoke(atelier);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Impossible d'appeler creerListeVide", e);
			}
			
			return arbreVide;
		}

		public boolean valider() {
			return aTester.equals(reference);
		}
	}
	
	
	public static void valider(ReportNodeViewModel report, Object atelier, Method methodeCreerNoeud) {
		PaireArbres.atelier = atelier;
		PaireArbres.methodeCreerArbre = methodeCreerNoeud;
		ValidateurArbre.continuerDeValider = true;

		int testId = 0;
		
		List<PaireArbres> tests = new ArrayList<>();
		
		PaireArbres vide01 = new PaireArbres();
		PaireArbres vide02 = new PaireArbres();
		tests.add(vide01);
		tests.add(vide02);

		try {

			htmlOut = new FileWriter(new File(htmlFilename(vide01.aTester)));
			htmlOut.write("<html>");
			htmlOut.write("<body>");
			htmlOut.write("<div style='max-width:80%; margin:auto;'>");

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(PaireArbres paire : tests) {
			for(int i = 0; i < 10; i++) {
				validateAdd(report, paire, testId);
				testId++;
			}
		}

		PaireArbres vide03 = new PaireArbres();
		validateRacine(report, vide03, testId);
		testId++;

		for(PaireArbres paire : tests) {
			validateRacine(report, paire, testId);
			testId++;
		}

		// TOUS LES NOEUDS
		validateTousLesNoeuds(report, vide03, testId);
		testId++;

		for(PaireArbres paire : tests) {
			validateTousLesNoeuds(report, paire, testId);
			testId++;
		}

		// FIND
		validateFind(report, vide03, testId);
		testId++;

		for(PaireArbres paire : tests) {
			for(int i = 0; i < 10; i++) {
				validateFind(report, paire, testId);
				testId++;
			}
		}

		// SIZE
		validateNombreDeNoeuds(report, vide03, testId);
		testId++;

		for(PaireArbres paire : tests) {
			validateNombreDeNoeuds(report, paire, testId);
			testId++;
		}

		// Retirer
		validateRetirer(report, vide03, null, testId);
		testId++;
		
		for(PaireArbres paire : tests) {
			Noeud<Character> racine = paire.reference.racine();
			if(racine != null) {
				validateRetirer(report, paire, racine.getValeur(), testId);
				testId++;
			}

			for(int i = 0; i < 15; i++) {
				validateRetirer(report, paire, null, testId);
				testId++;
			}
		}

		try {

			htmlOut.write("</div>");
			htmlOut.write("</body>");
			htmlOut.write("</html>");
			htmlOut.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		report.setExpectedNumberOfSubReports(testId);
	}

	private static void validateTousLesNoeuds(ReportNodeViewModel report, PaireArbres test, int testId) {
		T.call(ValidateurArbre.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);

		thisReport.setTitle(testName);

		htmlTestName(testName);

		tablePourValeurDeRetour(page);

		valeurAvantOperation(test, testName, page);
		
		try {
			page.append("<td>");
			page.append(String.format("arbre.tousLesNoeuds();"));
			page.append("</td>");

			List<Noeud<Character>> noeudsUsager = test.aTester.tousLesNoeuds();
			List<Noeud<Character>> noeudsReference = test.reference.tousLesNoeuds();
			
			List<Character> valeursUsager = new ArrayList<>();
			List<Character> valeursReference = new ArrayList<>();
			
			for(Noeud<Character> noeudUsager : noeudsUsager) {
				valeursUsager.add(noeudUsager.getValeur());
			}
			
			for(Noeud<Character> noeudReference : noeudsReference) {
				valeursReference.add(noeudReference.getValeur());
			}

			if(valeursUsager.equals(valeursReference)) {
				valeurDeRetourApresOK(valeursUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(valeursUsager, valeursReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			thisReport.setState(ValidationState.CRASH);
			thisReport.setHtmlPage(e.toString());
			htmlTestPage(page);
		}
		
	}


	private static void validateNombreDeNoeuds(ReportNodeViewModel report, PaireArbres test, int testId) {
		T.call(ValidateurArbre.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);
		thisReport.setTitle(testName);
		
		htmlTestName(testName);
		
		tablePourValeurDeRetour(page);

		valeurAvantOperation(test, testName, page);
		
		try {
			page.append("<td>");
			page.append(String.format("arbre.nombreDeNoeuds();"));
			page.append("</td>");

			int nombreUsager = test.aTester.nombreDeNoeuds();
			int nombreReference =  test.reference.nombreDeNoeuds();
			
			if(nombreUsager == nombreReference) {
				valeurDeRetourApresOK(nombreUsager, thisReport, page);
			}else {
				valeurDeRetourApresErreur(nombreUsager, nombreReference, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			thisReport.setState(ValidationState.CRASH);
			thisReport.setHtmlPage(e.toString());
			htmlTestPage(page);
		}
		
	}

	private static void validateFind(ReportNodeViewModel report, PaireArbres test, int testId) {
		T.call(ValidateurArbre.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);
		thisReport.setTitle(testName);

		htmlTestName(testName);
		
		tablePourValeurApres(page);

		valeurAvantOperation(test, testName, page);
		
		try {
			List<Noeud<Character>> lesNoeuds = test.reference.tousLesNoeuds();
			int valeur = 0;

			if(lesNoeuds.size() > 0 && alea.nextBoolean()) {
				valeur = lesNoeuds.get(alea.nextInt(lesNoeuds.size())).getValeur();
			}else {
				valeur = 97 + alea.nextInt(26);
			}

			page.append("<td>");
			page.append(String.format("arbre.trouverNoeud(%s);", (char) valeur));
			page.append("</td>");

			Noeud<Character> racineUsager = test.aTester.trouverNoeud((char) valeur);
			Noeud<Character> racineReference = test.reference.trouverNoeud((char) valeur);
			
			boolean siEgal = true;
			
			if(racineUsager == null && racineReference != null) {
				siEgal = false;
			}else if(racineUsager != null) {
				siEgal = racineUsager.equals(racineReference);
			}
			
			if(siEgal) {
				if(racineUsager != null) {
					valeurDeRetourApresOK(racineUsager.getValeur(), thisReport, page);
				}else {
					valeurDeRetourApresOK(racineUsager, thisReport, page);
				}
			}else {
				if(racineUsager != null && racineReference != null) {
					valeurDeRetourApresErreur(racineUsager.getValeur(), racineReference.getValeur(), thisReport, page);

				}else if(racineUsager != null) {
					valeurDeRetourApresErreur(racineUsager.getValeur(), null, thisReport, page);

				}else if(racineReference != null) {
					valeurDeRetourApresErreur(null, racineReference.getValeur(), thisReport, page);

				}else {
					valeurDeRetourApresErreur(null, null, thisReport, page);
				}
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			thisReport.setState(ValidationState.CRASH);
			thisReport.setHtmlPage(e.toString());
			htmlTestPage(page);
		}
		
	}

	private static void validateRetirer(ReportNodeViewModel report, PaireArbres test, Character valeurRacine, int testId) {
		T.call(ValidateurArbre.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);
		thisReport.setTitle(testName);

		htmlTestName(testName);
		
		tablePourValeurApres(page);

		valeurAvantOperation(test, testName, page);
		
		try {
			List<Noeud<Character>> lesNoeuds = test.reference.tousLesNoeuds();
			int valeur = 0;
			
			if(valeurRacine != null) {
				valeur = valeurRacine;
			}else if(lesNoeuds.size() > 0 && alea.nextBoolean()) {
				valeur = lesNoeuds.get(alea.nextInt(lesNoeuds.size())).getValeur();
			}else {
				valeur = 97 + alea.nextInt(26);
			}

			page.append("<td>");
			page.append(String.format("arbre.retirer(%s);", (char) valeur));
			page.append("</td>");

			test.aTester.retirer((char) valeur);
			test.reference.retirer((char) valeur);
			
			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			thisReport.setState(ValidationState.CRASH);
			thisReport.setHtmlPage(e.toString());
			htmlTestPage(page);
		}
		
	}

	private static void validateRacine(ReportNodeViewModel report, PaireArbres test, int testId) {
		T.call(ValidateurArbre.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);
		thisReport.setTitle(testName);

		htmlTestName(testName);

		tablePourValeurDeRetour(page);

		valeurAvantOperation(test, testName, page);
		
		try {
			page.append("<td>");
			page.append(String.format("arbre.racine();"));
			page.append("</td>");

			Noeud<Character> racineUsager = test.aTester.racine();
			Noeud<Character> racineReference = test.reference.racine();
			
			boolean siEgal = true;
			
			if(racineUsager == null && racineReference != null) {
				siEgal = false;
			}else if(racineUsager != null) {
				siEgal = racineUsager.equals(racineReference);
			}
			
			if(siEgal) {
				if(racineUsager != null) {
					valeurDeRetourApresOK(racineUsager.getValeur(), thisReport, page);
				}else {
					valeurDeRetourApresOK(racineUsager, thisReport, page);
				}
			}else {
				if(racineUsager != null && racineReference != null) {
					valeurDeRetourApresErreur(racineUsager.getValeur(), racineReference.getValeur(), thisReport, page);

				}else if(racineUsager != null) {
					valeurDeRetourApresErreur(racineUsager.getValeur(), null, thisReport, page);

				}else if(racineReference != null) {
					valeurDeRetourApresErreur(null, racineReference.getValeur(), thisReport, page);

				}else {
					valeurDeRetourApresErreur(null, null, thisReport, page);
				}
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			thisReport.setState(ValidationState.CRASH);
			thisReport.setHtmlPage(e.toString());
			htmlTestPage(page);
		}
		
	}

	private static void validateAdd(ReportNodeViewModel report, PaireArbres test, int testId) {
		T.call(ValidateurArbre.class);
		
		if(!continuerDeValider) return;
		
		ReportNodeViewModel thisReport = ReportViewModel.newSubReport();
		report.addSubReport(thisReport);
		
		StringBuilder page = new StringBuilder();
		
		String testName = testName(testId);
		report.updateTestId(testId);
		thisReport.setTitle(testName);

		htmlTestName(testName);

		tablePourValeurApres(page);

		valeurAvantOperation(test, testName, page);
		
		try {
			int element = 97 + alea.nextInt(26);

			page.append("<td>");
			page.append(String.format("arbre.ajouter(%s);", (char) element));
			page.append("</td>");

			test.aTester.ajouter((char) element);
			test.reference.ajouter((char) element);
			
			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			thisReport.setState(ValidationState.CRASH);
			thisReport.setHtmlPage(e.toString());
			htmlTestPage(page);
		}
		
	}

	private static void valeurApresErreur(PaireArbres test, 
			                              String testName, 
			                              ReportNodeViewModel thisReport, 
			                              StringBuilder page) {

		String graphName = graphName(test.aTester, testName, "erreur");
		writeObjectGraph(test.aTester, graphName);

		String graphSrc = graphSrc(graphName);

		page.append("<td>");
		if(ecrireGraphes) {
			page.append("<br>");
			page.append("<img src='" + graphSrc + "'/>");
		}else {
			page.append(test.aTester);
		}
		page.append("<br>");
		page.append("<br>");
		page.append("^^^ ERREUR ^^^");
		page.append("<br>");
		page.append("<br>");
		page.append("valeur attendue:<br><br>");
		page.append(test.reference);
		page.append("</td>");

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

	private static void valeurApresOK(PaireArbres test, 
			                          String testName, 
			                          ReportNodeViewModel thisReport, 
			                          StringBuilder page) {

		String graphName = graphName(test.aTester, testName, "ok");
		writeObjectGraph(test.aTester, graphName);

		String graphSrc = graphSrc(graphName);
		
		page.append("<td>");
		if(ecrireGraphes) {
			page.append("<br>");
			page.append("<img src='" + graphSrc + "'/>");
		}else {
			page.append(test.aTester);
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

	private static void valeurAvantOperation(PaireArbres test, 
			                                 String testName, 
			                                 StringBuilder page) {

		String graphName = graphName(test.aTester, testName, "avant");
		writeObjectGraph(test.aTester, graphName);

		String graphSrc = graphSrc(graphName);

		page.append("<tr>");
		page.append("<td>");
		if(ecrireGraphes) {
			page.append("<br>");
			page.append("<img src='" + graphSrc + "'/>");
		}else {
			page.append(test.aTester);
		}
		page.append("</td>");

	}

	private static void valeurPourCrash(ReportNodeViewModel thisReport, Exception e) {
		thisReport.setState(ValidationState.CRASH);
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);
		e.printStackTrace(printer);
		thisReport.setHtmlPage("<pre>" + writer.toString() + "</pre>");
		htmlTestPage("<pre>" + writer.toString() + "</pre>");
		
		continuerDeValider = false;
	}
}
