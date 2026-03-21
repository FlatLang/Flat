#ifndef NOVA_MACRO_LIB
#define NOVA_MACRO_LIB

#define DEFINED1(_NAME1_) DEFINEDO1(_NAME1_)
#define DEFINED2(_NAME1_, _NAME2_) DEFINEDO2(_NAME1_, _NAME2_)
#define DEFINED3(_NAME1_, _NAME2_, _NAME3_) DEFINEDO3(_NAME1_, _NAME2_, _NAME3_)
#define DEFINED4(_NAME1_, _NAME2_, _NAME3_, _NAME4_) DEFINEDO4(_NAME1_, _NAME2_, _NAME3_, _NAME4_)
#define DEFINED5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_) DEFINEDO5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_)

#define DEFINEDO1(_NAME1_) DEFINEDD1(_NAME1_, ||)
#define DEFINEDO2(_NAME1_, _NAME2_) DEFINEDD2(_NAME1_, _NAME2_, ||)
#define DEFINEDO3(_NAME1_, _NAME2_, _NAME3_) DEFINEDD3(_NAME1_, _NAME2_, _NAME3_, ||)
#define DEFINEDO4(_NAME1_, _NAME2_, _NAME3_, _NAME4_) DEFINEDD4(_NAME1_, _NAME2_, _NAME3_, _NAME4_, ||)
#define DEFINEDO5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_) DEFINEDD5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_, ||)

#define DEFINEDA1(_NAME1_) DEFINEDD1(_NAME1_, &&)
#define DEFINEDA2(_NAME1_, _NAME2_) DEFINEDD2(_NAME1_, _NAME2_, &&)
#define DEFINEDA3(_NAME1_, _NAME2_, _NAME3_) DEFINEDD3(_NAME1_, _NAME2_, _NAME3_, &&)
#define DEFINEDA4(_NAME1_, _NAME2_, _NAME3_, _NAME4_) DEFINEDD4(_NAME1_, _NAME2_, _NAME3_, _NAME4_, &&)
#define DEFINEDA5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_) DEFINEDD5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_, &&)

#define DEFINEDD1(_NAME1_, _DELIMITER_) defined(_NAME1_)
#define DEFINEDD2(_NAME1_, _NAME2_, _DELIMITER_) defined(_NAME1_) _DELIMITER_ defined(_NAME2_)
#define DEFINEDD3(_NAME1_, _NAME2_, _NAME3_, _DELIMITER_)  defined(_NAME1_) _DELIMITER_ defined(_NAME2_) _DELIMITER_ defined(_NAME3_)
#define DEFINEDD4(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _DELIMITER_)  defined(_NAME1_) _DELIMITER_ defined(_NAME2_) _DELIMITER_  defined(_NAME3_) _DELIMITER_ defined(_NAME4_)
#define DEFINEDD5(_NAME1_, _NAME2_, _NAME3_, _NAME4_, _NAME5_, _DELIMITER_)  defined(_NAME1_) _DELIMITER_ defined(_NAME2_) _DELIMITER_ defined(_NAME3_) _DELIMITER_ defined(_NAME4_) _DELIMITER_ defined(_NAME5_)

#ifdef TCC_OVERLOADS
#	define GET_MACRO2P(_1_, _2_, NAME, ...) NAME
#	define GET_MACRO3P(_1_, _2_, _3_, NAME, ...) NAME
#	define GET_MACRO4P(_1_, _2_, _3_, _4_, NAME, ...) NAME
#	define GET_MACRO5P(_1_, _2_, _3_, _4_, _5_, NAME, ...) NAME
#	define GET_MACRO6P(_1_, _2_, _3_, _4_, _5_, _6_, NAME, ...) NAME

#	define DEFINED(...) GET_MACRO5P(__VA_ARGS__, DEFINED5, DEFINED4, DEFINED3, DEFINED2, DEFINED1)(__VA_ARGS__)
#else
#	define GLUE(x, y) x y

#	define RETURN_ARG_COUNT(_1_, _2_, _3_, _4_, _5_, _6_, _7_, _8_, _9_, _10_, _11_, _12_, _13_, _14_, _15_, count, ...) count
#	define EXPAND_ARGS(args) RETURN_ARG_COUNT args
#	define COUNT_ARGS_MAX15(...) EXPAND_ARGS((__VA_ARGS__, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0))

#	define OVERLOAD_MACRO2(name, count) name##count
#	define OVERLOAD_MACRO1(name, count) OVERLOAD_MACRO2(name, count)
#	define OVERLOAD_MACRO(name, count) OVERLOAD_MACRO1(name, count)

#	define CALL_OVERLOAD(name, ...) GLUE(OVERLOAD_MACRO(name, COUNT_ARGS_MAX15(__VA_ARGS__)), (__VA_ARGS__))

#	define DEFINED(...) CALL_OVERLOAD(DEFINED, __VA_ARGS__)
#endif

#ifdef _MSC_VER
    typedef __int64 int64;
#else
#   include <stdint.h>
    typedef int64_t int64;
#endif

#if defined(_WIN32) && defined(USE_GC)
#	define unsigned_long_long unsigned int64
#	define long_long int64
#else
#	define unsigned_long_long unsigned long long
#	define long_long long long
#endif

#ifdef USE_GC
#	ifdef _WIN32
#		define GC_WIN32_THREADS
#	elif defined(__APPLE__) || defined(__linux__)
#		define GC_PTHREADS
#	endif

#	define NOVA_FREE GC_free
#	define NOVA_MALLOC GC_malloc
#	define NOVA_REALLOC GC_realloc
#	define free NOVA_FREE
#	define malloc NOVA_MALLOC
#	define realloc NOVA_REALLOC
#else
#	define NOVA_FREE free
#	define NOVA_MALLOC malloc
#	define NOVA_REALLOC realloc
#endif

#endif
