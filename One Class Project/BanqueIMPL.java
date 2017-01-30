package tn.iit.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import model.Compte;

@Stateless
public class BanqueIMPL implements BanqueLocal {
	@PersistenceContext(unitName = "comptes")
	private EntityManager em;

	@Override
	public Compte addCompte(Compte cp) {
		em.persist(cp);
		return cp;
	}
	@Override
	public void removeCompte(int rib) {
		em.remove(em.find(Compte.class, rib));
		
		
	}
	
	@Override
	public Compte getCompte(long rib) {
		Compte cp = em.find(Compte.class, rib);
		if (cp == null)
			throw new RuntimeException("Compte introuvable");

		return cp;
	}

	@Override
	public List<Compte> ListComptes() {
	
		return em.createQuery("select c from Compte c", Compte.class).getResultList();
	}

	@Override
	public void verser(long rib, float mt) {
		Compte cp = getCompte(rib);
		cp.setSolde(cp.getSolde() + mt);
	}

	@Override
	public void retirer(long rib, float mt) {
		Compte cp = getCompte(rib);
		if (cp.getSolde() < mt)
			throw new RuntimeException("solde insuffisant");
		cp.setSolde(cp.getSolde() - mt);

	}

	@Override
	public void virement(long rib1, long rib2, float mt) {
		retirer(rib1, mt);
		// FIXME
		verser(rib2, mt);
	}

	

}