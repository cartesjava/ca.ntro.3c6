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
package tutoriels.core.swing.views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JEditorPane;
import javax.swing.JPanel;

import ca.ntro.core.system.debug.T;
import tutoriels.core.performance_app.swing.views.PerformanceGraphViewSwing;
import tutoriels.core.performance_app.views.PerformanceGraphView;
import tutoriels.core.views.CurrentNodeView;
import tutoriels.swing.Theme;

public class CurrentNodeViewSwing extends JPanel implements CurrentNodeView {
	private static final long serialVersionUID = 8588135472839250862L;
	
	private JEditorPane htmlPane = new JEditorPane();
	private PerformanceGraphViewSwing performanceGraphView = new PerformanceGraphViewSwing();
	
	private static final String PERFORMANCE_GRAPH = "PerformanceGraphView";
	private static final String HTML_PAGE = "htmlPane";
	
	
	public CurrentNodeViewSwing() {
		super();
		
		setBackground(Color.RED);
		setLayout(new GridLayout(0,1));

		this.add(htmlPane);
		Theme.setColors(htmlPane);
		htmlPane.setEditable(false);
	}
	

	@Override
	public void step01FetchCommands() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void step02InstallEventListeners() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayHtml(String htmlContent) {
		T.call(this);

		htmlPane.setContentType("text/html");
		htmlPane.setText(htmlContent);
	}
}
