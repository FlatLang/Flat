#include "NativeMD5.h"

char* nova_md5(char* input)
{
	int n;
	int length;
    MD5_CTX c;
    unsigned char digest[16];
    char *out = (char*)malloc(33);
	
	length = strlen(input);
	
    MD5_Init(&c);

    while (length > 0)
    {
        if (length > 512)
        {
            MD5_Update(&c, input, 512);
        }
        else
        {
            MD5_Update(&c, input, length);
        }
        
        length -= 512;
        input += 512;
    }

    MD5_Final(digest, &c);

    for (n = 0; n < 16; ++n)
    {
        snprintf(&(out[n*2]), 16*2, "%02x", (unsigned int)digest[n]);
    }

    return out;
}