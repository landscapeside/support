package com.landside.support.https

import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val IgnoreSSLFactory =
  {
    var sc: SSLContext? = null
    try {
      sc = SSLContext.getInstance("TLS")
      sc.init(null, arrayOf<TrustManager>(
          object : X509TrustManager {
            @Throws(
                CertificateException::class
            )
            override fun checkClientTrusted(
              chain: Array<X509Certificate>,
              authType: String
            ) {
            }

            @Throws(
                CertificateException::class
            )
            override fun checkServerTrusted(
              chain: Array<X509Certificate>,
              authType: String
            ) {
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
              return arrayOf()
            }
          }
      ), SecureRandom())
    } catch (e1: NoSuchAlgorithmException) {
      e1.printStackTrace()
    } catch (e: KeyManagementException) {
      e.printStackTrace()
    }
    sc!!.socketFactory
  }
