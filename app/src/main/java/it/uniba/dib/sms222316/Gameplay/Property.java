package it.uniba.dib.sms222316.Gameplay;

public class Property
{
        private String nome , tipo , gruppo;
        private int costo , costoQuadro , prezzoVendita;
        private int posizione;

        private int affitto[];
        private int paints;

        private String descrizione;
        private Player Giocatore;

        //Costruttore per l'inizio della partita
        public Property(String nome,  String tipo, String gruppo,  String descrizione , int costo, int affitto[], int costoQuadro , int prezzoVendita , int posizione) {
            this.nome = nome;
            this.costo = costo;
            this.affitto = affitto;
            this.tipo = tipo;
            this.gruppo = gruppo;
            this.descrizione = descrizione;
            this.costoQuadro = costoQuadro;
            this.prezzoVendita = prezzoVendita;
            this.posizione = posizione;
            this.paints =0;
            this.Giocatore = null;
        }

        //Costruttore per il caricamento partita
        public Property(String nome, int costo, int affitto[], String tipo , String gruppo , String descrizione , int costoQuadro , int prezzoVendita , int posizione , int paints , Player Giocatore) {
            this.nome = nome;
            this.costo = costo;
            this.affitto = affitto;
            this.tipo = tipo;
            this.gruppo = gruppo;
            this.descrizione = descrizione;
            this.costoQuadro = costoQuadro;
            this.prezzoVendita = prezzoVendita;
            this.posizione = posizione;
            this.paints = paints;
            this.Giocatore = Giocatore;
        }



        public String getNome() {
            return nome;
        }

        public int getCosto() {
            return costo;
        }

        public int getAffitto(int paint) {
            return affitto[paint];
        }

        public String getGruppo() {
            return gruppo;
        }

        public int getCostoQuadro() {
            return costoQuadro;
        }

        public int getCostoVendita() {
            return prezzoVendita;
        }

        public int[] getAffitto() {
            return affitto;
        }

        public String getDescrizione() {
            return descrizione;
        }

        public String getTipo() {
                return tipo;
            }

        public void setPaints(int paints) {
            this.paints = paints;
        }

        public int getPaints() {
            return paints;
        }
        public boolean isAvaible()
        {
            if (this.Giocatore == null)return true;
            else return false;
        }

    public Player getGiocatore() {
        return Giocatore;
    }

    public void setGiocatore(Player giocatore) {
        Giocatore = giocatore;
    }
}
