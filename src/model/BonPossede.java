package model;

public class BonPossede {
    private int id;
    private int idMenage;
    private int idOffre;
    private int quantite;

    public BonPossede(int idMenage, int idOffre, int quantite) {
        this.idMenage = idMenage;
        this.idOffre = idOffre;
        this.quantite = quantite;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdMenage() {
		return idMenage;
	}

	public void setIdMenage(int idMenage) {
		this.idMenage = idMenage;
	}

	public int getIdOffre() {
		return idOffre;
	}

	public void setIdOffre(int idOffre) {
		this.idOffre = idOffre;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

    
}
