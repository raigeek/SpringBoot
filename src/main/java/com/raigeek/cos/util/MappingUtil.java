package com.raigeek.cos.util;

import com.raigeek.cos.vo.LabelVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class MappingUtil  {
    @Value("${mappingsBundleName}")
    private String mappingsBundleName;

    public List<LabelVO> getLabels(@NotNull Locale locale) {
        ResourceBundle labels = ResourceBundle.getBundle(this.mappingsBundleName, locale);
        return Collections.list(labels.getKeys()).stream().map(input -> {
            LabelVO output = new LabelVO();
            output.setKey(input);
            output.setValue(labels.getString(input));
            return output;
        }).collect(Collectors.toList());
    }
}
