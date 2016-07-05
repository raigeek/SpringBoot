package com.raigeek.cos.service;

import com.raigeek.cos.vo.LabelVO;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Locale;

public interface ContentService {
    /**
     * Returns a list of labels for the passed in locale
     *
     * @param locale    Use for localizing labels
     * @return          List of Labels
     */
    WrappedResponse<List<LabelVO>> getLabels(@NotNull Locale locale);

    /**
     * Returns a byte array based on the specified contentId. Returns null if the key is not found.
     *
     * @param contentId The content ID used to return the file
     * @return          A byte array containing the file
     */
    com.raigeek.cos.service.WrappedResponse<byte[]> getBinaryContent(@NotNull String contentId, @NotNull Locale locale);

    /**
     * Returns the specified text file as a String based on the specified contentId.
     *
     * @param contentId The content ID used to return the file
     * @return          A String containing the file content
     */
    com.raigeek.cos.service.WrappedResponse<String> getTextContent(@NotNull String contentId, @NotNull Locale locale);

}