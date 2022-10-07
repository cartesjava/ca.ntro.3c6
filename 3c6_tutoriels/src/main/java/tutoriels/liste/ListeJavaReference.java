package tutoriels.liste;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListeJavaReference<E extends Object> extends ListeJava<E> implements Serializable {
	private static final long serialVersionUID = -3517214214768508021L;

	public ListeJavaReference(Class<E> typeElement) {
		super(typeElement);
	}

	private List<E> elements = new ArrayList<>();

	@Override
	public void add(E e) {
		elements.add(e);
	}

	@Override
	public void insert(int i, E e) {
		elements.add(i,e);
	}

	@Override
	public void addAll(E[] elements) {
		for(E e : elements) {
			add(e);
		}
	}

	@Override
	public void set(int i, E e) {
		elements.set(i,e);
	}

	@Override
	public E get(int i) {
		return elements.get(i);
	}

	@Override
	public void clear() {
		elements.clear();
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	@Override
	public int indexOf(Object o) {
		return elements.indexOf(o);
	}

	@Override
	public void removeValue(Object o) {
		elements.remove(o);
	}

	@Override
	public void remove(int i) {
		elements.remove(i);
	}
}
