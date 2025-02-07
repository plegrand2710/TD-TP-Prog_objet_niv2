package td3;
import java.util.ArrayList;
import java.util.List;

public class PolynomialManager {
    private List<Polynomial> polynomials;

    public PolynomialManager() {
        polynomials = new ArrayList<>();
    }
    
    public void addPolynomial(Polynomial poly) {
        polynomials.add(poly);
    }
    
    public List<Polynomial> getPolynomials() {
        return polynomials;
    }
    
    public void clearPolynomials() {
        polynomials.clear();
    }
}