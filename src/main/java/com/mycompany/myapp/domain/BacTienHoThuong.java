package com.mycompany.myapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A BacTienHoThuong.
 */
@Entity
@Table(name = "bac_tien_ho_thuong")
public class BacTienHoThuong implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten_bac")
    private String tenBac;

    @Column(name = "gia_tri_bac")
    private Long giaTriBac;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BacTienHoThuong id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenBac() {
        return this.tenBac;
    }

    public BacTienHoThuong tenBac(String tenBac) {
        this.tenBac = tenBac;
        return this;
    }

    public void setTenBac(String tenBac) {
        this.tenBac = tenBac;
    }

    public Long getGiaTriBac() {
        return this.giaTriBac;
    }

    public BacTienHoThuong giaTriBac(Long giaTriBac) {
        this.giaTriBac = giaTriBac;
        return this;
    }

    public void setGiaTriBac(Long giaTriBac) {
        this.giaTriBac = giaTriBac;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BacTienHoThuong)) {
            return false;
        }
        return id != null && id.equals(((BacTienHoThuong) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BacTienHoThuong{" +
            "id=" + getId() +
            ", tenBac='" + getTenBac() + "'" +
            ", giaTriBac=" + getGiaTriBac() +
            "}";
    }
}
