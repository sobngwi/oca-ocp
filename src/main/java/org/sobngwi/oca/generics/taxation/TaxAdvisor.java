package org.sobngwi.oca.generics.taxation;

import java.util.ArrayList;
import java.util.List;

public class TaxAdvisor {
  public static void computeTaxes(Taxable t) {}
  public static void computeBulkTaxes(Taxable[] ta) {
    for(Taxable t : ta) {
      computeTaxes(t);
    }
    Corporation c = null;
    ta[0] = c; // Potentially runtime "ArrayStoreException"
  }

  // ? extends Something "Covariance"
  public static void computeBulkTaxes(List<? extends Taxable> lt) {
    for(Taxable t : lt) {
      computeTaxes(t);
    }
//    Taxable t = null;
//    lt.add(t);
  }

  // Known as Contravariance
  public static void addIndividualClients(List<? super Individual> lt) {
    Individual t = null;
    lt.add(t);

//    Individual i1 = lt.get(0);
  }

  public static void main(String[] args) {
    List<Taxable> clients = new ArrayList<>();
    computeBulkTaxes(clients);

    List<Individual> li = new ArrayList<>();
    computeBulkTaxes(li);

    Individual[] ia = new Individual[3];
    computeBulkTaxes(ia);

    List<Individual> liclients = new ArrayList<>();
    addIndividualClients(liclients);

    addIndividualClients(clients);
  }
}
