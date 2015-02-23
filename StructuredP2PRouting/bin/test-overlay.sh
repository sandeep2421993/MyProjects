    test_home=/s/chopin/l/grad/sandeepk/cs455Assignments/Assignment1/src
     
    for i in `cat machine_list`
    do
    	echo 'logging into '${i}
    	gnome-terminal -x bash -c "ssh -t ${i} 'cd $test_home; java cs455.overlay.node.MessagingNode 129.82.46.231 2424;bash;'" &
    done


