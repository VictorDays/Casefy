package com.casefy.service;

import java.io.File;
import java.io.IOException;

public interface  FileService {
    String salvar(String nomeArquivo, byte[] arquivo) throws IOException;
    File obter(String nomeArquivo); 
}
