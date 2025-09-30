package com.janseva.service;

import com.janseva.model.Complaint;
import com.janseva.repository.ComplaintRepository;
// Imports for real Google Vision API
// import com.google.cloud.vision.v1.*;
// import com.google.protobuf.ByteString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public Complaint createComplaint(Complaint complaint, byte[] imageBytes) throws IOException {
        // In a real app:
        // 1. Upload imageBytes to Google Cloud Storage or AWS S3.
        // 2. Get the public URL of the uploaded image.
        // 3. Set that URL to complaint.setPhotoUrl(imageUrl);
        complaint.setPhotoUrl("https://via.placeholder.com/300/FF0000/FFFFFF?text=Issue+Photo"); // Mock URL

        // 4. Analyze the image with Google Vision API.
        String detectedIssue = analyzeImage(imageBytes);
        complaint.setIssueType(detectedIssue);

        return complaintRepository.save(complaint);
    }

    private String analyzeImage(byte[] imageBytes) throws IOException {
        // --- MOCK AI LOGIC ---
        // For this demo, we'll just randomly assign an issue type.
        // This simulates the AI classification without needing API keys.
        String[] possibleIssues = {"POTHOLE", "GARBAGE_DUMP", "BROKEN_STREETLIGHT", "WATER_LOGGING"};
        Random random = new Random();
        return possibleIssues[random.nextInt(possibleIssues.length)];

        /*
        // --- REAL GOOGLE VISION API LOGIC ---
        // Uncomment this block and add your credentials to use the real API.
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            ByteString imgBytes = ByteString.copyFrom(imageBytes);
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder()
                    .addFeatures(feat)
                    .setImage(img)
                    .build();

            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(List.of(request));
            AnnotateImageResponse res = response.getResponses(0);

            if (res.hasError()) {
                System.err.println("Error: " + res.getError().getMessage());
                return "UNCATEGORIZED";
            }

            // Simple logic: return the label with the highest score.
            // A real app would have more sophisticated mapping logic.
            EntityAnnotation annotation = res.getLabelAnnotations(0);
            return annotation.getDescription().toUpperCase().replace(" ", "_");
        }
        */
    }
}