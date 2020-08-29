function toast(message, type) {
    bootoast.toast({
        message: message,
        type: type,
        position: 'top-center',
        icon: null,
        timeout: 2,
        animationDuration: 300,
        dismissible: true
    });
}