#ifndef NOVA_NATIVE_MD5
#define NOVA_NATIVE_MD5

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#if defined(__APPLE__)
#	define COMMON_DIGEST_FOR_OPENSSL
#	include <CommonCrypto/CommonDigest.h>
#	define SHA1 CC_SHA1
#else
#	include <openssl/md5.h>
#endif

char* nova_md5(char* input);

#endif