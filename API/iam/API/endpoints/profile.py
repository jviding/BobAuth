from django.http import JsonResponse, HttpResponseForbidden
from django.views.decorators.http import require_http_methods
import json

@require_http_methods(["GET"])
def profile(request):
    if request.user.is_authenticated:
        username = request.user.username
        email = request.user.email
        isAdmin = request.user.is_superuser
        return JsonResponse({
            "username": username,
            "email": email,
            "isAdmin": isAdmin
        })
    else:
        return HttpResponseForbidden("Failure")
