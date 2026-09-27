import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CartService } from '../../core/services/cart.service';
import { OrderService } from '../../core/services/order.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './checkout.component.html',
  styleUrl: './checkout.component.css',
})
export class CheckoutComponent implements OnInit {
  shippingAddress = '';
  placingOrder = false;
  errorMessage = '';

  constructor(
    public cartService: CartService,
    private orderService: OrderService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.cartService.refresh();
  }

  placeOrder(): void {
    if (!this.shippingAddress.trim()) {
      this.errorMessage = 'Please enter a shipping address.';
      return;
    }
    this.placingOrder = true;
    this.errorMessage = '';

    this.orderService.checkout({ shippingAddress: this.shippingAddress }).subscribe({
      next: (order) => {
        this.placingOrder = false;
        this.router.navigate(['/orders'], { queryParams: { placed: order.id } });
      },
      error: (err) => {
        this.placingOrder = false;
        this.errorMessage = err.error?.error || 'Could not place order.';
      },
    });
  }
}
