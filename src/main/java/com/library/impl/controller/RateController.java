package com.library.impl.controller;

import com.library.impl.db.entity.RateEntity;
import com.library.impl.service.RateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RateController {
    private final RateService rateService;

    @GetMapping("/rateBook/{id}")
    ModelAndView rateBook(@PathVariable Long id){
        RateEntity rateEntity = new RateEntity();
        rateEntity.setBookId(id);

        return new ModelAndView("rateBook",
                "rateEntity",
                rateEntity);
    }

    @PostMapping("/saveRating")
    public ModelAndView saveRating(@ModelAttribute RateEntity rateEntity,
                                   RedirectAttributes redirectAttributes) {
        if (rateEntity.getRate() == null) {
            redirectAttributes.addFlashAttribute("error", "Моля, изберете оценка.");
            return new ModelAndView("redirect:/rateBook/" + rateEntity.getBookId());
        }
        rateService.saveRating(rateEntity);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/showRates/{id}")
    ModelAndView showRates(@PathVariable Long id) {

        return new ModelAndView("showRates",
                "rates",
                rateService.findRatesByBookId(id));
    }
}
