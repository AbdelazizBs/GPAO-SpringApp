package com.housservice.housstock.model;

    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;
    import java.util.Date;
    import java.util.List;


    @Getter
    @Setter
    @Document(collection = "Plannification")
    public class Plannification {
        @Id
        private String id;
        private String refMachine;
        private List<Personnel>idPersonnels;
        private String nomEtape;
        private Article ligneCommandeClient;
        private Date heureDebutPrevue;
        private Date heureDebutReel;
        private Date heureFinPrevue;
        private Date heureFinReel;
        private int quantiteNonConforme;
        private int quantiteConforme;
        private int quantiteInitiale;
        private String commentaire;

        public Plannification() {
        }

        public Plannification(String refMachine, List<Personnel> idPersonnels, String nomEtape, Article ligneCommandeClient, Date heureDebutPrevue, Date heureDebutReel, Date heureFinPrevue, Date heureFinReel, int quantiteNonConforme, int quantiteConforme, int quantiteInitiale, String commentaire) {
        this.refMachine = refMachine;
        this.idPersonnels = idPersonnels;
        this.nomEtape = nomEtape;
        this.ligneCommandeClient = ligneCommandeClient;
        this.heureDebutPrevue = heureDebutPrevue;
        this.heureDebutReel = heureDebutReel;
        this.heureFinPrevue = heureFinPrevue;
        this.heureFinReel = heureFinReel;
        this.quantiteNonConforme = quantiteNonConforme;
        this.quantiteConforme = quantiteConforme;
        this.quantiteInitiale = quantiteInitiale;
        this.commentaire = commentaire;
    }
}
