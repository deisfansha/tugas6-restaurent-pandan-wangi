package com.example.restaurant_pandan_wangi.controller;

import com.example.restaurant_pandan_wangi.model.ApiResponse;
import com.example.restaurant_pandan_wangi.model.TableNumber;
import com.example.restaurant_pandan_wangi.service.TableNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tables")
public class TableNumberController {
    @Autowired
    private TableNumberService tableNumberService;

    // API untuk menampilkan semua daftar meja.
    @GetMapping("")
    public ResponseEntity getAllTable() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Success",
                        tableNumberService.getAllTable()
                ));
    }

    // API untuk menampilkan daftar meja berdasarkan status tersedia.
    @GetMapping("/actived/{isActive}")
    public ResponseEntity getAllTableByActive(@PathVariable boolean isActive) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Success",
                        tableNumberService.getAllTableByActive(isActive)
                ));
    }

    // API untuk menampilkan daftar meja berdasarkan status digunakan.
    @GetMapping("/in-used/{isUsed}")
    public ResponseEntity getAllTableByUse(@PathVariable boolean isUsed) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "Success",
                        tableNumberService.getAllTableByInUse(isUsed)
                ));
    }

    // API untuk menampilkan informasi meja berdasarkan ID Meja.
    @GetMapping("{id}")
    public ResponseEntity getAllTableById(@PathVariable long id) {
        if (tableNumberService.getTableById(id) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ApiResponse(
                            "Success",
                            tableNumberService.getTableById(id)
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(
                            tableNumberService.getMessage()
                    ));
        }

    }

    // API untuk menambahkan meja baru.
    @PostMapping("")
    public ResponseEntity addTable() {
        tableNumberService.add();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        "Success",
                        tableNumberService.getCurrent()
                ));
    }

    // API untuk memperbarui status tersedia meja berdasarkan ID Meja dan request.
    @PatchMapping("/{id}/activated")
    public ResponseEntity updateActivated(@PathVariable long id, @RequestBody TableNumber tableNumberRequest) {
        if (tableNumberService.updateStatus(id,tableNumberRequest.isActive())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            "Success",
                            tableNumberService.getCurrent()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            tableNumberService.getMessage()
                    ));
        }
    }

    // API untuk memperbarui status penggunaan meja berdasarakan ID Meja dan request.
    @PatchMapping("/in-used/{id}")
    public ResponseEntity updateInUsed(@PathVariable long id, @RequestBody TableNumber tableNumberRequest) {
        if (tableNumberService.updateInUse(id,tableNumberRequest.isTableInUse())) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ApiResponse(
                            "Success",
                            tableNumberService.getCurrent()
                    ));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(
                            tableNumberService.getMessage()
                    ));
        }
    }
}
