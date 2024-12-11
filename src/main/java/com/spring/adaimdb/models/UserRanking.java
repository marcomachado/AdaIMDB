package com.spring.adaimdb.models;

import jakarta.persistence.*;

@Entity
@Table(name = "RANKING_VIEW")
public class UserRanking {

    @Id
    @Column(name = "id_fake")
    private Long idFake;
    @OneToOne
    private User user;
    @Column(name = "score_final")
    private int scoreFinal;

    public UserRanking() {
    }

    public UserRanking(User user, int scoreFinal) {
        this.user = user;
        this.scoreFinal = scoreFinal;
    }

    public Long getIdFake() {
        return idFake;
    }

    public void setIdFake(Long idFake) {
        this.idFake = idFake;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getScoreFinal() {
        return scoreFinal;
    }

    public void setScoreFinal(int scoreFinal) {
        this.scoreFinal = scoreFinal;
    }
}
