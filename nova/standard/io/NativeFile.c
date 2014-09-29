#include "NativeFile.h"

void setMaxOpenFiles(int num)
{
	#ifdef __APPLE__
		struct rlimit rlp;
		
		getrlimit(RLIMIT_NOFILE, &rlp);
		rlp.rlim_cur = num;
		setrlimit(RLIMIT_NOFILE, &rlp);
	#else
		_setmaxstdio(num);
	#endif
}

int getMaxOpenFiles()
{
	#ifdef __APPLE__
		struct rlimit rlp;
		
		getrlimit(RLIMIT_NOFILE, &rlp);
		
		return rlp.rlim_max;
	#else
		return _getmaxstdio();
	#endif
}
