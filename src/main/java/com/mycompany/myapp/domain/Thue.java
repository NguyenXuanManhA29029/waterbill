package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Thue.
 */
@Entity
@Table(name = "thue")
public class Thue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten_thue")
    private String tenThue;

    @Column(name = "gia_tri_thue")
    private Long giaTriThue;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Thue id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenThue() {
        return this.tenThue;
    }

    public Thue tenThue(String tenThue) {
        this.tenThue = tenThue;
        return this;
    }

    public void setTenThue(String tenThue) {
        this.tenThue = tenThue;
    }

    public Long getGiaTriThue() {
        return this.giaTriThue;
    }

    public Thue giaTriThue(Long giaTriThue) {
        this.giaTriThue = giaTriThue;
        return this;
    }

    public void setGiaTriThue(Long giaTriThue) {
        this.giaTriThue = giaTriThue;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Thue)) {
            return false;
        }
        return id != null && id.equals(((Thue) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Thue{" +
            "id=" + getId() +
            ", tenThue='" + getTenThue() + "'" +
            ", giaTriThue=" + getGiaTriThue() +
            "}";
    }
}
