package tutoriels.interfaces.atelier01;

import java.util.List;


@SuppressWarnings("rawtypes")
public interface SelectionAtelier01 {

	/**
	 * 
	 * Retourner les éléments plus grand que la borneInferieure
	 * et plus petit que la borneSuperieure
	 * 
	 * @param elements
	 * @param borneInferieure
	 * @param borneSuperieure
	 * @return
	 */
	List<Comparable> selectionner(List<Comparable> elements, 
			                                         Comparable borneInferieure, 
			                                         Comparable borneSuperieure);


}
