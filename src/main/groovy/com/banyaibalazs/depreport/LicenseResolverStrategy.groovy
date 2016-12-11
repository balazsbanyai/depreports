import com.banyaibalazs.depreport.LicenseData

public interface LicenseResolverStrategy {
    Optional<LicenseData> resolve(File dependency)
}