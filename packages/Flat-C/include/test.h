#ifndef TEST_H
#define TEST_H

#ifdef WIN32
#	define unsigned_long_long unsigned __int64
#	define long_long __int64
#else
#	define unsigned_long_long unsigned long long
#	define long_long long long
#endif

#endif