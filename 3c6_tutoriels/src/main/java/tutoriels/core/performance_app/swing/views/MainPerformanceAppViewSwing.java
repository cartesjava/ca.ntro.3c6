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
package tutoriels.core.performance_app.swing.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.performance_app.views.MainPerformanceAppView;
import tutoriels.core.performance_app.views.PerformanceGraphView;
import tutoriels.swing.Theme;

@SuppressWarnings("serial")
public class MainPerformanceAppViewSwing extends JFrame implements NtroView, MainPerformanceAppView {
	
	private JTabbedPane tabbedPane = new JTabbedPane();

	public MainPerformanceAppViewSwing(String title) {
		super(title);
		T.call(this);
	}

	public void initialize() {
		T.call(this);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch (Exception e) {}

		setLayout(new GridLayout(1,0));
		setSize(1200, 600);
		
		this.add(tabbedPane);
		Theme.setColors(tabbedPane);
		//pack();
		setVisible(true);
		
		// FIXME: this should be called by Ntro
		step01FetchCommands();
		step02InstallEventListeners();
	}

	@Override
	public void step01FetchCommands() {
		T.call(this);
	}

	@Override
	public void step02InstallEventListeners() {
		T.call(this);
	}

	@Override
	public PerformanceGraphView addGraphView(String title) {
		T.call(this);

		PerformanceGraphViewSwing graphView = new PerformanceGraphViewSwing();
		Theme.setColors(graphView);
		
		int newTabIndex = tabbedPane.getTabCount();
		tabbedPane.addTab(title, graphView);

		Component newTab = tabbedPane.getTabComponentAt(newTabIndex);
		if(newTab != null) {
			Theme.setColors(newTab);
		}

		return graphView;
	}
}
