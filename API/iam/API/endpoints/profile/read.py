from django.http import JsonResponse, HttpResponseForbidden

def readProfile(request):
    if request.user.is_authenticated:
        return JsonResponse({
            "userID": request.user.id,
            "username": request.user.username,
            "email": request.user.email,
            "isAdmin": request.user.is_superuser
        })
    else:
        return HttpResponseForbidden("Not authorized")
