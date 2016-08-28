#include "NativeFile.h"

void setMaxOpenFiles(int num)
{
	#ifdef __APPLE__
		struct rlimit rlp;
		
		getrlimit(RLIMIT_NOFILE, &rlp);
		rlp.rlim_cur = num;
		setrlimit(RLIMIT_NOFILE, &rlp);
	#elif defined(_WIN32)
		_setmaxstdio(num);
	#endif
}

int getMaxOpenFiles()
{
	#ifdef __APPLE__
		struct rlimit rlp;
		
		getrlimit(RLIMIT_NOFILE, &rlp);
		
		return rlp.rlim_max;
	#elif defined(_WIN32)
		return _getmaxstdio();
	#endif
	
	return 100;
}
