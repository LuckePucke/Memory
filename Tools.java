import java.util.*;

public class Tools {
	
	public static void randomOrder(Object a[]) {
		
		Random r = new Random();
		Object[] b = a.clone();
		
		int n = 0;
		while(b.length > 0) {
			int x = r.nextInt(b.length);
			a[n] = b[x];
			b = removeElement(b, x);
			n++;
		}
	}
	
	public static Object[] removeElement(Object a[], int i) {
		
		Object[] b = new Object[a.length -1];
		
		for(int x = 0; x < i; x++) {
			b[x] = a[x];
		}
		for(int x = i + 1; x < a.length; x++) {
			b[x-1] = a[x];
		}
		
		return b;
	}
}
