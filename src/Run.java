import org.hibernate.*;

import test.*;
import bdd.*;

public class Run {

	/**
	 * @param args
	 * @throws HibernateException 
	 */
	public static void main(String[] args) {
		
		Session s = HibernateUtil.currentSession();
		Transaction tx = s.beginTransaction();
		
		Personne p = new Personne();
		p.setId(new Integer(0));
		p.setNom("Marmin");
		p.setPrenom("Thibaut");
		
		s.save(p);
		tx.commit();
		
		HibernateUtil.closeSession();

	}

}
