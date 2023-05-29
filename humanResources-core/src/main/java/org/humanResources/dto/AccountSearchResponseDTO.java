package org.humanResources.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashSet;
import java.util.Set;

public class AccountSearchResponseDTO {

    private Long id;

    private String name;

    /*
    private String description;

    private String season_or_annual;

    private String where_made_country;

    private ProductDTO.CompanyDTO maker_company;

    public Set<ProductCategoryAssociationDTO> categories = new HashSet<>();


    public Set<ProductMediaDTO> media = new HashSet<>();

    private boolean can_edit;
*/

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
/*
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getSeason_or_annual() {
        return season_or_annual;
    }

    public void setSeason_or_annual(String season_or_annual) {
        this.season_or_annual = season_or_annual;
    }


    public Set<ProductCategoryAssociationDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<ProductCategoryAssociationDTO> categories) {
        this.categories = categories;
    }


    public ProductDTO.CompanyDTO getMaker_company() {
        return maker_company;
    }

    public void setMaker_company(ProductDTO.CompanyDTO maker_company) {
        this.maker_company = maker_company;
    }

    public String getWhere_made_country() {
        return where_made_country;
    }

    public void setWhere_made_country(String where_made_country) {
        this.where_made_country = where_made_country;
    }


    public Set<ProductMediaDTO> getMedia() {
        return media;
    }

    public void setMedia(Set<ProductMediaDTO> media) {
        this.media.clear();
        this.media.addAll(media);
    }




    public boolean getCan_edit() {
        return can_edit;
    }

    public void setCan_edit(boolean canEdit) {
        this.can_edit = canEdit;
    }

    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof AccountDTO)) return false;

        AccountSearchResponseDTO that = (AccountSearchResponseDTO) o;

        return new EqualsBuilder()
                .append(getId(), that.getId())
                .append(getName(), that.getName())
                /*.append(getDescription(), that.getDescription())
                .append(getSeason_or_annual(), that.getSeason_or_annual())
                .append(getWhere_made_country(), that.getWhere_made_country())
                .append(getMaker_company(), that.getMaker_company())
                .append(getCategories(), that.getCategories())*/
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                /*.append(getDescription())
                .append(getSeason_or_annual())
                .append(getWhere_made_country())
                .append(getMaker_company())
                .append(getCategories())*/
                .toHashCode();
    }

    @Override
    public String toString() {
        return "AccountSearchResponseDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                /*", description='" + description + '\'' +
                ", season_or_annual='" + season_or_annual + '\'' +
                ", where_made_country='" + where_made_country + '\'' +
                ", maker_company=" + maker_company +
                ", categories=" + categories +
                ", media=" + media +*/
                '}';
    }

}
