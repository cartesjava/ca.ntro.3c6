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

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import ca.ntro.core.initialization.Ntro;
import ca.ntro.core.reflection.object_graph.ObjectGraph;

public class Validateur {

	static boolean continuerDeValider = true;

	static boolean ecrireGraphes = true;

	static FileWriter htmlOut = null;
	
	static Random alea = new Random();

	static String testName(int testId) {
		return String.format("test%02d", testId);
	}

	static void htmlTestName(String testName) {
		try {
			htmlOut.write("<h1>" + testName + "</h1>");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	static void htmlTestPage(StringBuilder page) {
		htmlTestPage(page.toString());
	}

	static void htmlTestPage(String page) {
		try {
			htmlOut.write(page.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String htmlFilename(Object object) {
		return Ntro.reflection().simpleName(object.getClass()) + ".html";
	}

	static String htmlFilename(Object object, String forcedClassName) {
		if(forcedClassName != null) {
			return forcedClassName + ".html";
			
		}else {
			return htmlFilename(object);
		}
	}

	static String graphName(Object object, String testName, String suffix) {
		return Ntro.reflection().simpleName(object.getClass()) + "_" + testName + "_" + suffix;
	}

	static void writeObjectGraph(Object object, String graphName) {
		if(!ecrireGraphes) return;

		ObjectGraph graph = Ntro.reflection().graphFromObject(object, graphName);
		graph.write(Ntro.graphWriter());
	}
	
	static String graphSrc(String graphName) {

		Path graphPath = Paths.get(".", "_storage", "graphs", graphName + ".png");
		
		String graphSrc = "";
		

		try {

			graphSrc = graphPath.toUri().toURL().toString();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return graphSrc;
	}

	static void cssTable(StringBuilder page) {
		page.append("<style>");
		page.append("table{border:1px solid black;}");
		page.append(System.lineSeparator());
		page.append("tr{border:1px solid black;}");
		page.append(System.lineSeparator());
		page.append("td{border:1px solid black;}");
		page.append(System.lineSeparator());
		page.append("th{border:1px solid black;}");
		page.append("</style>");

	}

	static void tablePourValeurDeRetour(StringBuilder page) {
		cssTable(page);
		page.append("<table>");
		page.append("<tr>");
		page.append("<th>");
		page.append("Valeur avant opération");
		page.append("</th>");
		page.append("<th>");
		page.append("Opération");
		page.append("</th>");
		page.append("<th>");
		page.append("Valeur de retour");
		page.append("</th>");
		page.append("</tr>");
	}

	static void tablePourValeurApres(StringBuilder page) {
		cssTable(page);
		page.append("<table>");
		page.append("<tr>");
		page.append("<th>");
		page.append("Valeur avant opération");
		page.append("</th>");
		page.append("<th>");
		page.append("Opération");
		page.append("</th>");
		page.append("<th>");
		page.append("Valeur après opération");
		page.append("</th>");
		page.append("</tr>");
	}

}