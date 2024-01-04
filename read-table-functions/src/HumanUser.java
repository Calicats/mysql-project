/* ENTRY POINT: DBPlatforma.java */

public abstract class HumanUser {
    public int getId() {
        return id;
    }

    public String getCnp() {
        return cnp;
    }

    public String getNume() {
        return nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getParola() {
        return parola;
    }

    public int getIdRol() {
        return idRol;
    }

    protected int id;
    protected String cnp;
    protected String nume;
    protected String adresa;
    protected String nrTelefon;
    protected String email;
    protected String username;
    protected String parola;
    protected int idRol;

    protected HumanUser() {
    }

    protected HumanUser(int id, String cnp, String nume, String adresa, String nrTelefon, String email, String username, String parola, int idRol) {
        this.id = id;
        this.cnp = cnp;
        this.nume = nume;
        this.adresa = adresa;
        this.nrTelefon = nrTelefon;
        this.email = email;
        this.username = username;
        this.parola = parola;
        this.idRol = idRol;
    }
}
