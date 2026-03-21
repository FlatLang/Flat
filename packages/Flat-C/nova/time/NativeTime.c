#include "NativeTime.h"

long_long currentTimeMillis()
{
	struct timeb tmb;
	long_long value;

	ftime(&tmb);
	/*printf("tmb.time     = %ld (seconds)\n", tmb.time);
	printf("tmb.millitm  = %d (mlliseconds)\n", tmb.millitm);*/

	value  = tmb.time;
	value *= 1000;
	value += tmb.millitm;

	return value;
}