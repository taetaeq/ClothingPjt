import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GoogleCloudStorageTest {
    public static void main(String[] args) {
        try {
            // Google Cloud Storage 연결
            Storage storage = StorageOptions.getDefaultInstance().getService();

            // 프로젝트 ID 확인
            System.out.println("Google Cloud Storage 인증 성공!");
            System.out.println("프로젝트 ID: " + storage.getOptions().getProjectId());
        } catch (Exception e) {
            System.err.println("Google Cloud Storage 인증 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
