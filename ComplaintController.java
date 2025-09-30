package com.janseva.controller;

import com.janseva.dto.ComplaintDTO;
import com.janseva.model.Complaint;
import com.janseva.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin // Allows requests from our HTML file on a different origin
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @PostMapping
    public ResponseEntity<Complaint> createComplaint(
            @RequestParam("description") String description,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam("photo") MultipartFile photo) {
        try {
            Complaint newComplaint = new Complaint();
            newComplaint.setDescription(description);
            newComplaint.setLatitude(latitude);
            newComplaint.setLongitude(longitude);
            // In a real app, you would associate this with the logged-in user.

            Complaint savedComplaint = complaintService.createComplaint(newComplaint, photo.getBytes());
            return new ResponseEntity<>(savedComplaint, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}