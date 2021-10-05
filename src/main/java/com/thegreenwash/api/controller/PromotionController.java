package com.thegreenwash.api.controller;

import com.thegreenwash.api.model.Promotion;
import com.thegreenwash.api.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

        private final PromotionService promService;

        @Autowired
        public PromotionController(PromotionService promService) {
            this.promService = promService;
        }


        @GetMapping
        public List<Promotion> getPromotions() {
            return promService.getPromotions();

        }
}
