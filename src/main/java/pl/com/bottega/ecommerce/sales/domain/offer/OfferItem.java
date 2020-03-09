/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class OfferItem {

    // product
    private Product product;

    private int quantity;

    private Money totalCost;

    // discount
    private String discountCause;

    private Money discount;

    public OfferItem(String productId, BigDecimal productPrice, String productName, Date productSnapshotDate, String productType,
            int quantity, String productCurrency, String totalCostCurrency) {
        this(productId, productPrice, productName, productSnapshotDate, productType, productCurrency, quantity, totalCostCurrency, null,
                null, null);
    }

    public OfferItem(String productId, BigDecimal productPrice, String productName, Date productSnapshotDate, String productType,
            String productCurrency, int quantity, String totalCostCurrency, BigDecimal discountPrice, String discountCurrency,
            String discountCause) {
        this.product = new Product(productId, productName, productSnapshotDate, productType, productPrice, productCurrency);

        this.quantity = quantity;
        this.discount = new Money(discountPrice, discountCurrency);
        this.discountCause = discountCause;

        BigDecimal discountValue = new BigDecimal(0);
        if (discount != null) {
            discountValue = discountValue.add(discount.getDenomination());
        }

        this.totalCost = new Money(productPrice.multiply(new BigDecimal(quantity)).subtract(discountValue), totalCostCurrency);
    }

    public String getProductId() {
        return product.getId();
    }

    public BigDecimal getProductPrice() {
        return product.getPrice();
    }

    public String getProductName() {
        return product.getName();
    }

    public Date getProductSnapshotDate() {
        return product.getSnapshotDate();
    }

    public String getProductType() {
        return product.getType();
    }

    public BigDecimal getTotalCost() {
        return totalCost.getDenomination();
    }

    public BigDecimal getDiscount() {
        return discount.getDenomination();
    }

    public String getDiscountCause() {
        return discountCause;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OfferItem offerItem = (OfferItem) o;
        return quantity == offerItem.quantity && Objects.equals(product, offerItem.product) && Objects.equals(totalCost,
                offerItem.totalCost) && Objects.equals(discountCause, offerItem.discountCause) && Objects.equals(discount,
                offerItem.discount);
    }

    @Override public int hashCode() {
        return Objects.hash(product, quantity, totalCost, discountCause, discount);
    }

    /**
     * @param other
     * @param delta acceptable percentage difference
     * @return
     */
    public boolean sameAs(OfferItem other, double delta) {
        if (product == null) {
            if (other.product != null) {
                return false;
            }
        }
        if (totalCost == null) {
            if (other.totalCost != null) {
                return false;
            }
        }
        if (discount == null) {
            if (other.discount != null) {
                return false;
            }
        }

        if (quantity != other.quantity) {
            return false;
        }

        BigDecimal max;
        BigDecimal min;
        if (totalCost.getDenomination().compareTo(other.totalCost.getDenomination()) > 0) {
            max = totalCost.getDenomination();
            min = other.totalCost.getDenomination();
        } else {
            max = other.totalCost.getDenomination();
            min = totalCost.getDenomination();
        }

        BigDecimal difference = max.subtract(min);
        BigDecimal acceptableDelta = max.multiply(BigDecimal.valueOf(delta / 100));

        return acceptableDelta.compareTo(difference) > 0;
    }

}
