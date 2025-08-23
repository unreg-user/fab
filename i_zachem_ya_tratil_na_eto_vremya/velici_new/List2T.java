package wta.fun;

import java.util.ArrayList;

public class List2T {
	public ArrayList<Type1> list1;
	public ArrayList<Type2> list2;

	public List2T(ArrayList<Type1> list1, ArrayList<Type2> list2){
		this.list1=list1;
		this.list2=list2;
	}

	public List2T(){
		this(new ArrayList<>(), new ArrayList<>());
	}

	public void add(Type1 obj){
		list1.add(obj);
	}

	public void add(Type2 obj){
		list2.add(obj);
	}

	public static  List2T of(Object... objs){
		List2T list=new List2T();
		for (Object i : objs){
			if (i instanceof Type1){
				list.add(i);
			}else if (i instanceof  Type2){
				list.add(i);
			}
		}
		return list;
	}
}
