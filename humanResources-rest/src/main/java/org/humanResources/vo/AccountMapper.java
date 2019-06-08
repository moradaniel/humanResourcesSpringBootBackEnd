package org.humanResources.vo;


import org.humanResources.dto.AccountDTO;
import org.humanResources.security.model.AccountImpl;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Mapper(uses = {/*MakerCompanyMapper.class, ProductCategoryAssociationMapper.class,
                SpecificProductPackTypeMapper.class,
                ProductMentionMapper.class,
                ProductMediaMapper.class,
                ProductAwardMapper.class,
                ProductCertificationAssociationMapper.class,
                DateMapper.class*/
                }, componentModel = "spring")
public abstract class AccountMapper {

    /*
    @Autowired
    protected ProductService productService;

    @Autowired
    protected UserService userService;

    @Mappings({
            @Mapping(target = "id", source = "product.id"),
            @Mapping(target = "name", source = "product.name"),
            @Mapping(target = "description", source = "product.description"),
            @Mapping(target = "maker_company", source = "product.company"),
            @Mapping(target = "season_or_annual", source = "product.seasonOrAnnual"),
            @Mapping(target = "where_made_country", source = "product.whereMadeCountry"),
            @Mapping(target = "categories", source = "product.productsCategories"),
            @Mapping(target = "media", source = "product.media")


    })
    public abstract ProductSearchResponseDTO productToProductSearchResponseDTO(Product product);
*/


    @Mappings({
            @Mapping(target = "id", source = "account.id"),
            @Mapping(target = "name", source = "account.name")/*,
            @Mapping(target = "description", source = "product.description"),
            @Mapping(target = "how_made", source = "product.howMade"),

            @Mapping(target = "maker_company", source = "product.company"),
            @Mapping(target = "season_or_annual", source = "product.seasonOrAnnual"),
            @Mapping(target = "where_made_city", source = "product.whereMadeCity"),
            @Mapping(target = "where_made_state", source = "product.whereMadeState"),
            @Mapping(target = "where_made_country", source = "product.whereMadeCountry"),

            @Mapping(target = "ingredients", source = "product.ingredients"),
            @Mapping(target = "hidden", source = "product.hidden"),
            @Mapping(target = "promotion_link", source = "product.promotionLink"),
            @Mapping(target = "availability_start_date", source = "product.availabilityStartDate"),
            @Mapping(target = "availability_end_date", source = "product.availabilityEndDate"),


            @Mapping(target = "handling_notes", source = "product.handlingNotes"),
            @Mapping(target = "other_notes", source = "product.otherNotes"),
            @Mapping(target = "serving_sugg", source = "product.servingSugg"),
            @Mapping(target = "pairings", source = "product.pairings"),
            @Mapping(target = "recipes", source = "product.recipes"),
            @Mapping(target = "recipes_link", source = "product.recipesLink"),
            @Mapping(target = "url_slug_name", source = "product.urlSlugName"),


            @Mapping(target = "categories", source = "product.productsCategories"),
            @Mapping(target = "specific_product_pack_types", source = "product.specificProductPackTypes"),
            @Mapping(target = "mentions", source = "product.mentions"),
            @Mapping(target = "awards", source = "product.awards"),
            @Mapping(target = "certifications", source = "product.productCertifications"),
            @Mapping(target = "media", source = "product.media"),
            @Mapping(target = "created_at", source = "product.audit.createdOn"),
            @Mapping(target = "updated_at", source = "product.audit.updatedOn"),
            @Mapping(target = "deleted_at", source = "product.audit.deletedOn")*/


    })
    public abstract AccountDTO accountToAccountDTO(AccountImpl account);


    @Mappings({
            @Mapping(target = "id", source = "accountDTO.id"),
            @Mapping(target = "name", source = "accountDTO.name")/*,
            @Mapping(target = "description", source = "productCreateDTO.description"),
            @Mapping(target = "howMade", source = "productCreateDTO.how_made"),

            @Mapping(target = "company", source = "productCreateDTO.maker_company"),
            @Mapping(target = "seasonOrAnnual", source = "productCreateDTO.season_or_annual"),
            @Mapping(target = "whereMadeCity", source = "productCreateDTO.where_made_city"),
            @Mapping(target = "whereMadeState", source = "productCreateDTO.where_made_state"),
            @Mapping(target = "whereMadeCountry", source = "productCreateDTO.where_made_country"),

            @Mapping(target = "ingredients", source = "productCreateDTO.ingredients"),
            @Mapping(target = "hidden", source = "productCreateDTO.hidden"),
            @Mapping(target = "promotionLink", source = "productCreateDTO.promotion_link"),
            @Mapping(target = "availabilityStartDate", source = "productCreateDTO.availability_start_date"),
            @Mapping(target = "availabilityEndDate", source = "productCreateDTO.availability_end_date"),


            @Mapping(target = "handlingNotes", source = "productCreateDTO.handling_notes"),
            @Mapping(target = "otherNotes", source = "productCreateDTO.other_notes"),
            @Mapping(target = "servingSugg", source = "productCreateDTO.serving_sugg"),
            @Mapping(target = "pairings", source = "productCreateDTO.pairings"),
            @Mapping(target = "recipes", source = "productCreateDTO.recipes"),
            @Mapping(target = "recipesLink", source = "productCreateDTO.recipes_link"),
            @Mapping(target = "urlSlugName", source = "productCreateDTO.url_slug_name"),

            @Mapping(target = "productsCategories", source = "productCreateDTO.categories"),
            @Mapping(target = "specificProductPackTypes", source = "productCreateDTO.specific_product_pack_types"),
            @Mapping(target = "mentions", source = "productCreateDTO.mentions"),
            @Mapping(target = "awards", source = "productCreateDTO.awards"),
            @Mapping(target = "productCertifications", source = "productCreateDTO.certifications")

            //media is updated independently*/



    })
    public abstract AccountImpl accountDTOToAccount(AccountDTO accountDTO);

/*
    @AfterMapping
    protected void setProductToTheProductRelations(@MappingTarget Product product) {

        product.getProductsCategories().forEach(association -> {
            association.setProduct(product);
        });

        product.getSpecificProductPackTypes().forEach(specificProductPackType ->
            specificProductPackType.setProduct(product)
        );

        product.getMentions().forEach(mention ->
                mention.setProduct(product)
        );

        product.getAwards().forEach(award ->
                award.setProduct(product)
        );

        product.getProductCertifications().forEach(association -> {
            association.setProduct(product);
        });
    }

    @AfterMapping
    protected void setAllowedOperations(Product product, @MappingTarget ProductDTO productCreateDTO) {

        productCreateDTO.setCan_edit(productService.isProductEditableByUser(product, userService.getCurrentUser()));

    }

    @AfterMapping
    protected void setAllowedOperations(Product product, @MappingTarget ProductSearchResponseDTO productSearchResponseDTO) {

        productSearchResponseDTO.setCan_edit(productService.isProductEditableByUser(product, userService.getCurrentUser()));

    }

    @AfterMapping
    protected void setProductImageUrl(Product product,
                                      @MappingTarget ProductSearchResponseDTO productSearchResponseDTO) {

        if(productSearchResponseDTO != null && productSearchResponseDTO.getMedia()!= null) {
            List<ProductMediaDTO> productMediaList = new ArrayList<>(productSearchResponseDTO.getMedia());
            Comparator c = Collections.reverseOrder(new Comparator<ProductMediaDTO>() {
                @Override
                public int compare(ProductMediaDTO o1, ProductMediaDTO o2) {
                    return o1.getUpdated_at().compareTo(o2.getUpdated_at());
                }
            });

            Collections.sort(productMediaList,c);
            if(!productMediaList.isEmpty()) {
                Set<ProductMediaDTO> productMediaDTOSet = new HashSet<>();
                productMediaDTOSet.add(productMediaList.get(0));
                productSearchResponseDTO.setMedia(productMediaDTOSet);
            }
        }
    }*/

}
