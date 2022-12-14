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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import ca.ntro.core.commands.CommandFactory;
import ca.ntro.core.system.debug.T;
import ca.ntro.core.views.NtroView;
import tutoriels.core.commands.select_node.SelectNode;
import tutoriels.core.views.ValidationAppMainView;
import tutoriels.swing.Theme;
import tutoriels.core.views.CurrentNodeView;
import tutoriels.core.views.ReportNodeView;

@SuppressWarnings("serial")
public class ValidationAppMainViewSwing extends JFrame implements NtroView, ValidationAppMainView {

	private JTree tree;
	private CustomTreeCellRenderer treeCellRenderer = new CustomTreeCellRenderer();
	private DefaultMutableTreeNode rootNode;
	
	private ReportNodeView rootReportView;
	private long rootId = 0;
	
	private SelectNode selectNodeCommand;
	
	private CurrentNodeViewSwing currentNodeView;
	

	public ValidationAppMainViewSwing(String title) {
		super(title);
		T.call(this);
	}

	public void initialize() {
		T.call(this);

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); 
		} catch (Exception e) {}

		Theme.setColors(this);
		setLayout(new GridLayout(1,0));
		setSize(1200, 600);
		
		rootNode = new DefaultMutableTreeNode(new NodeInfo(rootId));

		tree = new JTree(rootNode);
		Theme.setColors(tree);
		tree.getSelectionModel().setSelectionMode
				(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree.setCellRenderer(treeCellRenderer);
		
		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);
 
		currentNodeView = new CurrentNodeViewSwing();
		
		// Create rootReportView
		rootReportView = new ReportNodeViewSwing(tree, rootNode, treeCellRenderer);

		JScrollPane currentNodeContainer = new JScrollPane(currentNodeView);
 
		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(currentNodeContainer);
 
		Dimension minimumSize = new Dimension(200, 50);
		currentNodeContainer.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(300); 
		splitPane.setPreferredSize(new Dimension(1200, 600));
 
		//Add the split pane to this panel.
		add(splitPane);
		
		//pack();
		setVisible(true);
		
		
		// FIXME: this should be called by Ntro
		step01FetchCommands();
		step02InstallEventListeners();
	}

	@Override
	public ReportNodeView getRootReportView() {
		T.call(this);

		return rootReportView;
	}


	@Override
	public void step01FetchCommands() {
		T.call(this);
		
		selectNodeCommand = (SelectNode) CommandFactory.buildCommand(SelectNode.class);
	}

	@Override
	public void step02InstallEventListeners() {
		T.call(this);

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				T.call(this);

				reactToItemSelection();
			}
		});
		
	}

	private void reactToItemSelection() {
		T.call(this);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		
		if(node != null) {
			
			NodeInfo nodeInfo = (NodeInfo) node.getUserObject();
			
			selectNodeCommand.setNodeId(nodeInfo.getId());
			
			selectNodeCommand.execute();
		}
	}

	@Override
	public CurrentNodeView getCurrentNodeView() {
		return currentNodeView;
	}

}
