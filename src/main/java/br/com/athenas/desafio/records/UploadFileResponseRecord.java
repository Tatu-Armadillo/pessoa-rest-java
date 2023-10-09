package br.com.athenas.desafio.records;

public record UploadFileResponseRecord(
        String fileName,
        String fileDownloadUri,
        String fileType,
        Long size) {

}
