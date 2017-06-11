#!/bin/bash
TESTSDIR="tests-ef-eval-201412012000"

# Directorio base do projecto
PROJDIR="project"

# Directorio onde se encontra o material de apoio (po-uilib, poof-support)
SUPPORTDIR=/usr/share/java

TEMPDIR="tests.res.d"

SUCCESS=0
FAIL=0
STRRESULTS=""

exec > >(tee $TEMPDIR/tests.res)

for file in $TESTSDIR/*.in
do
    filename=$(basename $file)
    filename=${filename%%.*}

    if [ -f $TESTSDIR/$filename.import ]
    then
      java -Dimport=$TESTSDIR/$filename.import -Din=$TESTSDIR/$filename.in -Dout=$TEMPDIR/test.outhyp poof.textui.Shell
    else
      java -Din=$TESTSDIR/$filename.in -Dout=$TEMPDIR/test.outhyp poof.textui.Shell
    fi


    echo "====================$filename===================="
    if diff -w $TESTSDIR/expected/$filename.out $TEMPDIR/test.outhyp; then

      #echo "TEST PASSED!"
      SUCCESS=$[SUCCESS+1]
      STRRESULTS="$STRRESULTS$filename - OK\n"
    else
      echo "output differs from expected"
      FAIL=$[FAIL+1]
      STRRESULTS="$STRRESULTS$filename - FAIL\n"
    fi
done
echo "=====================TEST RESULTS====================="
echo -e $STRRESULTS
echo "SUCCESS: $SUCCESS"
echo "FAIL: $FAIL"

echo ""
echo "Generated '$TEMPDIR/tests.res'"
