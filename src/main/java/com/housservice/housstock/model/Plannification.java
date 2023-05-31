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
        private List<String> personnels;
        private String nomEtape;
        private Article ligneCommandeClient;
        private int quantiteNonConforme;
        private int quantiteConforme;
        private int quantiteInitiale;
        private String commentaire;
        private Boolean plan;
        private Boolean etat;
        private List<PlanEtapes> etapes;
        private String ref;
        private String idComm;


        private int progress;
        private int counter;

        public Plannification() {
        }


        public Plannification(String id, List<String> personnels, String nomEtape, Article ligneCommandeClient, int quantiteNonConforme, int quantiteConforme, int quantiteInitiale, String commentaire, Boolean plan, Boolean etat, List<PlanEtapes> etapes, String ref, String idComm, int progress) {
            this.id = id;
            this.personnels = personnels;
            this.nomEtape = nomEtape;
            this.ligneCommandeClient = ligneCommandeClient;
            this.quantiteNonConforme = quantiteNonConforme;
            this.quantiteConforme = quantiteConforme;
            this.quantiteInitiale = quantiteInitiale;
            this.commentaire = commentaire;
            this.plan = plan;
            this.etat = etat;
            this.etapes = etapes;
            this.ref = ref;
            this.idComm = idComm;
            this.progress = progress;
        }
    }
