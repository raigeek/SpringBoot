package com.raigeek.cos.controller;

import org.springframework.web.bind.annotation.RestController;
import com.raigeek.cos.service.ContentService;
import com.raigeek.cos.service.WrappedResponse;
import com.raigeek.cos.vo.LabelVO;
import org.apache.commons.lang3.LocaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/rest/cmscontent")
public class CmsContentController {

    /**
     * Gets the list of labels specific to a MTaas to a variant
     * nothing is specified.
     * @param localeParam       Locale
     * @return                  List of Labels.
     */

    private static final Logger LOG = LoggerFactory.getLogger(CmsContentController.class);

    @Autowired
    ContentService contentService;


    @RequestMapping(value = "/label", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LabelVO>> getLabels(@RequestParam(value = "localeParam", required = true) String localeParam) {

        Locale locale = null;
        if (StringUtils.isNotBlank(localeParam)) {
            locale = this.parseLocale(localeParam);
        }
        WrappedResponse<List<LabelVO>> response = this.contentService.getLabels(locale);
        return new ResponseEntity<List<LabelVO>>(response.getResponse(),response.getStatus());
    }

    /**
     * Returns a byte array based on the specified contentId and locale
     * nothing is specified.
     *
     * @param pathVariables     Map containing the path variables.
     * @param localeParam       Locale
     * @return                  byte array.
     */

    @RequestMapping(value = { "/images/{path}/{contentId}.{suffix}", "/images/{contentId}.{suffix}", "/fonts/{path}/{contentId}.{suffix}", "/fonts/{contentId}.{suffix}",
            "/{path}/{contentId}.{suffix}", "/{contentId}.{suffix}" }, method = RequestMethod.GET, produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
            MediaType.IMAGE_PNG_VALUE, "image/svg+xml", "application/x-font-ttf", "application/x-font-woff", "application/vnd.ms-fontobject" })
    public ResponseEntity<byte[]> getBinaryContent(@PathVariable Map<String, String> pathVariables, @RequestParam String localeParam) {
        String contentId = StringUtils.join(pathVariables.get("contentId"), ".", pathVariables.get("suffix"));
        if (pathVariables.containsKey("path")) {
            contentId = StringUtils.join(pathVariables.get("path"), "/", contentId);
        }
        Locale locale = null;
        if (StringUtils.isNotBlank(localeParam)) {
            locale = this.parseLocale(localeParam);
        }
        WrappedResponse<byte[]> response = contentService.getBinaryContent(contentId,locale);
        return new ResponseEntity<byte[]>(response.getResponse(),response.getStatus());
    }

     /**
     * Returns a text-based file (html or css, etc.) for the specified contentId and variant.
     *
     *
     * @param pathVariables     Map containing the path variables.
     * @param localeParam       Locale
     * @return                  The file as a String.
     */

    @RequestMapping(value = { "/html/{path}/{contentId}.{suffix}", "/html/{contentId}.{suffix}", "/css/{path}/{contentId}.{suffix}", "/css/{contentId}.{suffix}",
            "/{path}/{contentId}.{suffix}", "/{contentId}.{suffix}" }, method = RequestMethod.GET, produces = { MediaType.TEXT_HTML_VALUE, MediaType.APPLICATION_XHTML_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE, "text/css" })
    public ResponseEntity<String> getTextContent(@PathVariable Map<String, String> pathVariables, @RequestParam String localeParam) {
        String contentId = StringUtils.join(pathVariables.get("contentId"), ".", pathVariables.get("suffix"));
        if (pathVariables.containsKey("path")) {
            contentId = StringUtils.join(pathVariables.get("path"), "/", contentId);
        }
        Locale locale = null;
        if (StringUtils.isNotBlank(localeParam)) {
            locale = this.parseLocale(localeParam);
        }
        WrappedResponse<String> response = contentService.getTextContent(contentId,locale);
        return new ResponseEntity<String>(response.getResponse(),response.getStatus());
    }

    private Locale parseLocale(String locale) {
        try {
            return LocaleUtils.toLocale(locale);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
