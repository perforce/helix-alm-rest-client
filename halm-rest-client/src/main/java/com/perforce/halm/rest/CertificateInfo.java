/*
 * *****************************************************************************
 * The MIT License (MIT)
 * 
 * Copyright (c) 2022, Perforce Software, Inc.  
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of 
 * this software and associated documentation files (the "Software"), to deal in 
 * the Software without restriction, including without limitation the rights to use, 
 * copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the 
 * Software, and to permit persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all 
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 * SOFTWARE.
 * *****************************************************************************
 */

package com.perforce.halm.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Certificate information object for conveying the certificate validity and handling error conditions
 */
public class CertificateInfo {
    private CertificateStatus status = CertificateStatus.INVALID;
    private String errorMessage = "";
    private final List<String> fingerprints = new ArrayList<>();
    private final List<String> pemCertificates = new ArrayList<>();

    public CertificateStatus getStatus() {
        return status;
    }
    public void setStatus(CertificateStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage != null ? errorMessage : ""; }

    public List<String> getFingerprints() { return fingerprints; }
    public void setFingerprints(List<String> certificates) {
        this.fingerprints.clear();
        this.fingerprints.addAll(certificates);
    }

    public List<String> getPemCertificates() { return pemCertificates; }
    public void setPemCertificates(List<String> pemCertificates) {
        this.pemCertificates.clear();
        this.pemCertificates.addAll(pemCertificates);
    }
}
