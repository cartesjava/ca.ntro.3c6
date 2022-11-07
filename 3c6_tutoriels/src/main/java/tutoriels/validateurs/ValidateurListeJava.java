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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import ca.ntro.core.initialization.Ntro;
import ca.ntro.core.reflection.object_graph.ObjectGraph;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.system.log.Log;
import tutoriels.core.models.reports.ReportNodeViewModel;
import tutoriels.core.models.reports.ReportViewModel;
import tutoriels.core.models.reports.values.ValidationState;
import tutoriels.liste.ListeJava;
import tutoriels.liste.ListeJavaReference;

public class ValidateurListeJava extends Validateur {

	private static class PaireDeListes {
		public ListeJava<Character> aTester;
		public ListeJava<Character> reference;
		
		public static Object atelier;
		public static Method methodeCreerListe;
		
		public PaireDeListes() {
				aTester = creerListeVide(atelier, methodeCreerListe);
				reference = new ListeJavaReference<>(Character.class);
		}

		private ListeJava<Character> creerListeVide(Object atelier, Method methodeCreerListe){
			
			ListeJava<Character> listeVide = null;
			
			try {

				listeVide = (ListeJava<Character>) methodeCreerListe.invoke(atelier);

			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {

				Log.fatalError("Impossible d'appeler creerListeVide", e);
			}
			
			return listeVide;
		}

		public boolean valider() {
			return aTester.equals(reference);
		}
	}
	
	
	public static void valider(ReportNodeViewModel report, Object atelier, Method methodeCreerListe, boolean ecrireGraphes) {
		ValidateurListeJava.ecrireGraphes = ecrireGraphes;
		PaireDeListes.atelier = atelier;
		PaireDeListes.methodeCreerListe = methodeCreerListe;
		ValidateurListeJava.continuerDeValider = true;
		
		int testId = 0;
		
		List<PaireDeListes> tests = new ArrayList<>();
		
		// 1 liste vide qu'on va tester 5 fois
		PaireDeListes vide01 = new PaireDeListes();
		tests.add(vide01);
		
		try {

			htmlOut = new FileWriter(new File(htmlFilename(vide01.aTester)));
			htmlOut.write("<html>");
			htmlOut.write("<body>");
			htmlOut.write("<div style='max-width:80%; margin:auto;'>");

		} catch (IOException e) {
			e.printStackTrace();
		}

		// tester add 8 fois de suite sur la même liste
		for(int i = 0; i < 15; i++) {
			validateAdd(report, vide01, testId);
			testId++;
		}

		// ré-ajouter la liste vide
		PaireDeListes vide02 = new PaireDeListes();
		tests.add(vide02);

		// tester addAll 2 fois
		validateAddAll(report, vide02, testId, new Character[] {});
		testId++;
		validateAddAll(report, vide02, testId, new Character[] {'a','b','c','d','e','f','g'});
		testId++;

		PaireDeListes vide03 = new PaireDeListes();
		tests.add(vide03);

		// Insérer au début quand vide
		validateInsert(report, vide03, testId, 0);
		testId++;
		
		// Insérer à la fin quand non-vide
		PaireDeListes auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			validateInsert(report, auHasard, testId, auHasard.reference.size()-1);
			testId++;
		}

		// Insérer au début quand non-vide
		auHasard = tests.get(alea.nextInt(tests.size()));
		validateInsert(report, auHasard, testId, 0);
		testId++;

		// Insérer au hasard quand non-vide
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			int indice = 1 + alea.nextInt(auHasard.reference.size());
			validateInsert(report, auHasard, testId, indice);
			testId++;
		}

		// set au hasard
		for(int i = 0; i < 3; i++) {
			auHasard = tests.get(alea.nextInt(tests.size()));
			validateSet(report, auHasard, testId);
			testId++;
		}

		// get au hasard
		for(int i = 0; i < 3; i++) {
			auHasard = tests.get(alea.nextInt(tests.size()));
			validateGet(report, auHasard, testId);
			testId++;
		}

		// un size vide
		PaireDeListes vide04 = new PaireDeListes();
		validateSize(report, vide04, testId);
		testId++;

		// 2 size au hasard
		for(int i = 0; i < 2; i++) {
			auHasard = tests.get(alea.nextInt(tests.size()));
			validateSize(report, auHasard, testId);
			testId++;
		}

		// un isEmpty vide
		PaireDeListes vide05 = new PaireDeListes();
		validateIsEmpty(report, vide05, testId);
		testId++;

		// 2 isEmpty au hasard
		for(int i = 0; i < 2; i++) {
			auHasard = tests.get(alea.nextInt(tests.size()));
			validateIsEmpty(report, auHasard, testId);
			testId++;
		}
		
		// contains sur vide
		int element = 97 + alea.nextInt(26);
		PaireDeListes vide06 = new PaireDeListes();
		validateContains(report, vide06, testId, (char) element);
		testId++;
		
		// contains sur non-vide, element abstent
		auHasard = tests.get(alea.nextInt(tests.size()));
		element = 97 + alea.nextInt(26);
		validateContains(report, auHasard, testId, (char) element);
		testId++;

		// contains sur non-vide, element présent
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			element = auHasard.reference.get(alea.nextInt(auHasard.reference.size()));
			validateContains(report, auHasard, testId, (char) element);
			testId++;
		}

		// indexOf sur vide
		element = 97 + alea.nextInt(26);
		PaireDeListes vide07 = new PaireDeListes();
		validateIndexOf(report, vide07, testId, (char) element);
		testId++;
		
		// indexOf sur non-vide, element abstent
		auHasard = tests.get(alea.nextInt(tests.size()));
		element = 97 + alea.nextInt(26);
		validateIndexOf(report, auHasard, testId, (char) element);
		testId++;

		// indexOf sur non-vide, element présent
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			element = auHasard.reference.get(alea.nextInt(auHasard.reference.size()));
			validateIndexOf(report, auHasard, testId, (char) element);
			testId++;
		}

		// removeValue sur vide
		element = 97 + alea.nextInt(26);
		PaireDeListes vide08 = new PaireDeListes();
		validateRemoveValue(report, vide08, testId, (char) element);
		testId++;
		
		// removeValue sur non-vide, element abstent
		auHasard = tests.get(alea.nextInt(tests.size()));
		element = 97 + alea.nextInt(26);
		validateRemoveValue(report, auHasard, testId, (char) element);
		testId++;

		// removeValue sur non-vide, element au début
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			element = auHasard.reference.get(0);
			validateRemoveValue(report, auHasard, testId, (char) element);
			testId++;
		}

		// removeValue sur non-vide, element à la fin
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			element = auHasard.reference.get(auHasard.reference.size()-1);
			validateRemoveValue(report, auHasard, testId, (char) element);
			testId++;
		}

		// removeValue sur non-vide, element aléatoire	
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			element = auHasard.reference.get(alea.nextInt(auHasard.reference.size()));
			validateRemoveValue(report, auHasard, testId, (char) element);
			testId++;
		}

		// remove sur non-vide, element au début
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			validateRemove(report, auHasard, testId, 0);
			testId++;
		}

		// remove sur non-vide, element à la fin
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			validateRemove(report, auHasard, testId, auHasard.reference.size()-1);
			testId++;
		}

		// removeValue sur non-vide, element aléatoire	
		auHasard = tests.get(alea.nextInt(tests.size()));
		if(auHasard.reference.size() > 0) {
			validateRemove(report, auHasard, testId, alea.nextInt(auHasard.reference.size()));
			testId++;
		}

		
		
		
		// tester clear à la fin!
		for(int i = 0; i < 3; i++) {
			auHasard = tests.get(alea.nextInt(tests.size()));
			validateClear(report, auHasard, testId);
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

	private static void validateRemove(ReportNodeViewModel report, PaireDeListes test, int testId, int index) {
		T.call(ValidateurListeJava.class);

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

			page.append("<td>");
			page.append(String.format("liste.remove(%s);", index));
			page.append("</td>");

			test.aTester.remove(index);
			test.reference.remove(index);

			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurApresCrash(thisReport, e);
		}

	}
	
	private static void validateRemoveValue(ReportNodeViewModel report, PaireDeListes test, int testId, char element) {
		T.call(ValidateurListeJava.class);

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

			page.append("<td>");
			page.append(String.format("liste.removeValue(%s);", element));
			page.append("</td>");

			test.aTester.removeValue(element);
			test.reference.removeValue(element);

			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}

	}
	
	
	private static void validateIndexOf(ReportNodeViewModel report, PaireDeListes test, int testId, char element) {
		T.call(ValidateurListeJava.class);

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
			page.append(String.format("liste.indexOf(%s);", element));
			page.append("</td>");

			int index01 = test.aTester.indexOf(element);
			int index02 = test.reference.indexOf(element);
			
			if(index01 == index02) {
				valeurDeRetourApresOK(index01, thisReport, page);
			}else {
				valeurDeRetourApresErreur(index01, index02, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}

	}
	

	private static void validateContains(ReportNodeViewModel report, PaireDeListes test, int testId, char element) {
		T.call(ValidateurListeJava.class);

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
			page.append(String.format("liste.contains(%s);", element));
			page.append("</td>");

			boolean contains01 = test.aTester.contains(element);
			boolean contains02 = test.reference.contains(element);
			
			if(contains01 == contains02) {
				valeurDeRetourApresOK(contains01, thisReport, page);
			}else {
				valeurDeRetourApresErreur(contains01, contains02, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}

	}
	
	private static void validateIsEmpty(ReportNodeViewModel report, PaireDeListes test, int testId) {
		T.call(ValidateurListeJava.class);

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
			page.append(String.format("liste.isEmpty();"));
			page.append("</td>");

			boolean isEmpty01 = test.aTester.isEmpty();
			boolean isEmpty02 = test.reference.isEmpty();
			
			if(isEmpty01 == isEmpty02) {
				valeurDeRetourApresOK(isEmpty01, thisReport, page);
			}else {
				valeurDeRetourApresErreur(isEmpty01, isEmpty02, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}

	}
	
	private static void validateSize(ReportNodeViewModel report, PaireDeListes test, int testId) {
		T.call(ValidateurListeJava.class);

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
			page.append(String.format("liste.size();"));
			page.append("</td>");

			int size01 = test.aTester.size();
			int size02 = test.reference.size();
			
			if(size01 == size02) {
				valeurDeRetourApresOK(size01, thisReport, page);
			}else {
				valeurDeRetourApresErreur(size01, size02, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}

	}
	

	private static void validateClear(ReportNodeViewModel report, PaireDeListes test, int testId) {
		T.call(ValidateurListeJava.class);

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
			page.append("<td>");
			page.append(String.format("liste.clear();"));
			page.append("</td>");

			test.aTester.clear();
			test.reference.clear();

			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}
	}

	private static void validateGet(ReportNodeViewModel report, PaireDeListes test, int testId) {
		T.call(ValidateurListeJava.class);

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
			int indice = alea.nextInt(test.reference.size());
			page.append("<td>");
			page.append(String.format("liste.get(%s);", indice));
			page.append("</td>");

			char element01 = test.aTester.get(indice);
			char element02 = test.reference.get(indice);
			
			if(element01 == element02) {
				valeurDeRetourApresOK(element01, thisReport, page);
			}else {
				valeurDeRetourApresErreur(element01, element02, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}

	}


	private static void validateSet(ReportNodeViewModel report, PaireDeListes test, int testId) {
		T.call(ValidateurListeJava.class);

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
			int indice = alea.nextInt(test.reference.size());
			int element = 97 + alea.nextInt(26);
			page.append("<td>");
			page.append(String.format("liste.set(%s,%s);", indice, (char) element));
			page.append("</td>");

			test.aTester.set(indice, (char) element);
			test.reference.set(indice, (char) element);

			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}
	}

	private static void validateInsert(ReportNodeViewModel report, PaireDeListes test, int testId, int indice) {
		T.call(ValidateurListeJava.class);

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
			page.append(String.format("liste.insert(%s,%s);",indice, (char) element));
			page.append("</td>");

			test.aTester.insert(indice, (char) element);
			test.reference.insert(indice, (char) element);

			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			page.append("</table><br><br>");
			valeurApresCrash(thisReport, e);
		}
	}

	private static void validateAddAll(ReportNodeViewModel report, PaireDeListes test, int testId, Character[] elements) {
		T.call(ValidateurListeJava.class);

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
			page.append("<td>");
			page.append(String.format("liste.addAll(%s);",Arrays.toString(elements)));
			page.append("</td>");

			test.aTester.addAll(elements);
			test.reference.addAll(elements);
			
			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {

			valeurApresCrash(thisReport, e);
		}
		
	}

	private static void validateAdd(ReportNodeViewModel report, PaireDeListes test, int testId) {
		T.call(ValidateurListeJava.class);

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
			page.append(String.format("liste.add(%s);", (char) element));
			page.append("</td>");

			test.aTester.add((char) element);
			test.reference.add((char) element);

			if(test.valider()) {
				valeurApresOK(test, testName, thisReport, page);
			}else {
				valeurApresErreur(test, testName, thisReport, page);
			}
			
			page.append("</table>");
			thisReport.setHtmlPage(page.toString());
			htmlTestPage(page);
			
		}catch(Exception e) {
			
			valeurApresCrash(thisReport, e);
			
		}
		
	}

	private static void valeurApresErreur(PaireDeListes test, 
			                              String testName, 
			                              ReportNodeViewModel thisReport, 
			                              StringBuilder page) {

		String graphName = graphName(test.aTester, testName, "erreur");
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
		page.append("<tr>");
		page.append("<td>");
		page.append(test.aTester);
		page.append("</td>");
		page.append("<td>");
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
		
		ValidateurListeJava.continuerDeValider = false;
	}

	private static void valeurDeRetourApresErreur(Object retourErreur, 
			                                      Object retourOK, 
			                                      ReportNodeViewModel thisReport, 
			                                      StringBuilder page) {
		page.append("<td>");
		page.append(retourErreur);
		page.append("<br>");
		page.append("<br>");
		page.append("valeur de retour attendue: ");
		page.append(retourOK);
		page.append("</td>");

		thisReport.setState(ValidationState.ERROR);

		ValidateurListeJava.continuerDeValider = false;
	}

	private static void valeurApresOK(PaireDeListes test, 
			                          String testName, 
			                          ReportNodeViewModel thisReport, 
			                          StringBuilder page) {

		String graphName = graphName(test.aTester, testName, "ok");
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

	private static void valeurAvantOperation(PaireDeListes test, 
			                                 String testName, 
			                                 StringBuilder page) {
		
		String graphName = graphName(test.aTester, testName, "avant");
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
	
	private static void valeurApresCrash(ReportNodeViewModel thisReport, Exception e) {
		thisReport.setState(ValidationState.CRASH);
		StringWriter writer = new StringWriter();
		PrintWriter printer = new PrintWriter(writer);
		e.printStackTrace(printer);
		thisReport.setHtmlPage("<pre>" + writer.toString() + "</pre>");
		htmlTestPage("<pre>" + writer.toString() + "</pre>");

		ValidateurListeJava.continuerDeValider = false;
	}
}
