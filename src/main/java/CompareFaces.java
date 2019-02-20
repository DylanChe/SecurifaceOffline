import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.BoundingBox;
import com.amazonaws.services.rekognition.model.CompareFacesMatch;
import com.amazonaws.services.rekognition.model.CompareFacesRequest;
import com.amazonaws.services.rekognition.model.CompareFacesResult;
import com.amazonaws.services.rekognition.model.ComparedFace;
import com.amazonaws.services.rekognition.model.Image;
import com.amazonaws.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

public class CompareFaces {

    public static void main(String[] args) throws Exception{

    }

    public static boolean compareFace(Image photo1, String photo2) throws IOException {
        AWSCredentials credentials;
        try {
            credentials = new ProfileCredentialsProvider().getCredentials();
        } catch (Exception e) {
            throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
                    + "Please make sure that your credentials file is at the correct "
                    + "location (/Users/userid/.aws/credentials), and is in valid format.", e);
        }

        AmazonRekognition rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(Regions.EU_WEST_1)
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();

        Image source = photo1;
        Image target = getImageUtil(photo2);

        Float similarityThreshold = 70F;
        CompareFacesResult compareFacesResult = callCompareFaces(source,
                target,
                similarityThreshold,
                rekognitionClient);

        List<CompareFacesMatch> faceDetails = compareFacesResult.getFaceMatches();
        if (faceDetails.size() > 0)
        {
            for (CompareFacesMatch match: faceDetails){
                ComparedFace face= match.getFace();
                BoundingBox position = face.getBoundingBox();
                System.out.println("Face at " + position.getLeft().toString()
                        + " " + position.getTop()
                        + " matches with " + face.getConfidence().toString()
                        + "% confidence.\n");
            }
            return true;
        }
        else
        {
            for (CompareFacesMatch match: faceDetails){
                ComparedFace face= match.getFace();
                BoundingBox position = face.getBoundingBox();
                System.out.println("Face at "+face.getConfidence().toString());

            }
            System.out.println("Face [" + photo1 + "] doesn't matches with [" + photo2 + "]\n");

            return false;
        }
    }

    private static CompareFacesResult callCompareFaces(Image sourceImage, Image targetImage,
                                                       Float similarityThreshold, AmazonRekognition amazonRekognition) {
        CompareFacesRequest compareFacesRequest = new CompareFacesRequest()
                .withSourceImage(sourceImage)
                .withTargetImage(targetImage)
                .withSimilarityThreshold(similarityThreshold);
        return amazonRekognition.compareFaces(compareFacesRequest);
    }

    private static Image getImageUtil(String key) throws IOException {
        ByteBuffer imageBytes;
        ClassLoader classLoader = new CompareFaces().getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(key)) {
            imageBytes = ByteBuffer.wrap(IOUtils.toByteArray(inputStream));

        }
        return new Image().withBytes(imageBytes);
    }
}