from django.http import JsonResponse, HttpResponseForbidden
from django.contrib.auth.models import User

def deleteUser(request):
    return HttpResponseForbidden("Not ready")
