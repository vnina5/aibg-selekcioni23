package aibg.selekcioni23.controller;

import aibg.selekcioni23.dto.*;
import aibg.selekcioni23.service.SelectionService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/selection")
@Getter
@Setter
public class SelectionController {
    private SelectionService selectionService;
    private Logger LOG = LoggerFactory.getLogger(SelectionController.class);

    @Autowired
    public SelectionController(SelectionService selectionService) {
        this.selectionService = selectionService;
    }

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<DTO> login(@RequestBody @Valid LoginRequestDTO dto) throws IOException {
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(selectionService.login(dto));

        DTO response = selectionService.login(dto);
//        LOG.info(response.toString());

        if (response instanceof LoginResponseDTO) {
            LOG.info("login response");
//            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
        } else {
            LOG.info("error response");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/join")
    public ResponseEntity<DTO> join(@RequestHeader("Authorization") String authorization) throws IOException {
        DTO response = selectionService.join(authorization);

        if (response instanceof JoinResponseDTO) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/myResult")
    public ResponseEntity<DTO> result(@RequestBody @Valid ResultRequestDTO dto,
                                      @RequestHeader("Authorization") String authorization) throws IOException {
        DTO response = selectionService.result(dto, authorization);

        if (response instanceof ResultResponseDTO) {
            return new ResponseEntity<>(response, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
